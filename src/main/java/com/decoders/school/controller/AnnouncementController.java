package com.decoders.school.controller;

import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.School;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.security.JwtTokenProvider;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementImageService announcementImageService;

    @Autowired
    private AnnouncementTypeService announcementTypeService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private Environment environment;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> createAnnouncement(HttpServletRequest request, @RequestBody AnnouncementResource announcementResource) {

        if (announcementResource.getTitle() == null
                || announcementResource.getText() == null
                || announcementResource.getImageList() == null
                || announcementResource.getImageList().isEmpty()
                || announcementResource.getExpireDate() == null
                || announcementResource.getAnnouncementTypeCode() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
        }

        String token = request.getHeader("Authorization");

        String[] tokenSplitter = token.split(" ");

        String username = jwtTokenProvider.getUsername(tokenSplitter[1]);

        School school = schoolService.findSchoolByUsername(username);

        if (school == null) {
            throw new ResourceException(HttpStatus.UNAUTHORIZED, "invalid_token");
        }

        Announcement announcement = announcementService.save(announcementResource.toAnnouncement());

        List<AnnouncementImage> announcementImageList = new ArrayList<>(announcementResource.getImageList().size());

        announcementResource.getImageList().forEach(announcementImageResource -> {
            announcementImageResource.setUrl(Utils.archiveFile(environment.getProperty("attachment_url"), environment.getProperty("attachment_path"), announcementImageResource.getUrl()));
            AnnouncementImage announcementImage = announcementImageResource.toAnnouncementImage();
            announcementImage.setAnnouncement(announcement);
            announcementImage = announcementImageService.save(announcementImage);
            announcementImageList.add(announcementImage);
        });

        announcement.setAnnouncementImageList(announcementImageList);

        announcementResource = AnnouncementResource.toResource(announcement);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(announcementResource);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> createAnnouncement(HttpServletRequest request,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "size", required = false) Integer size) {

        AnnouncementType announcementType = announcementTypeService.findAnnouncementTypeByCode("PUBLIC");

        Page<Announcement> announcementList = announcementService.findAnnouncement(announcementType, statusService.findStatusByCode("ACTIVE"), page, size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(announcementList.getTotalElements(), announcementList.getTotalPages(), AnnouncementResource.toResource(announcementList.getContent())));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

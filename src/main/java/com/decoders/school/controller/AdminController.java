package com.decoders.school.controller;

import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;
import com.decoders.school.entities.School;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AcademicYearResource;
import com.decoders.school.resource.AnnouncementImageResource;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.security.JwtTokenProvider;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementImageService announcementImageService;

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private Environment environment;


    @RequestMapping(value = "/announcement/create", method = RequestMethod.POST)
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

        messageBody.setStatus("20");
        messageBody.setText("OK");
        messageBody.setBody(announcementResource);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

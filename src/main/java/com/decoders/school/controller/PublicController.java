package com.decoders.school.controller;

import com.decoders.school.Utils.NotificationMessage;
import com.decoders.school.Utils.PushNotificationHandler;
import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.*;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.resource.SchoolResource;
import com.decoders.school.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementTypeService announcementTypeService;

    @Autowired
    private StatusService statusService;

//    @Autowired
//    private MobileDeviceService mobileDeviceService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findSchoolData(HttpServletRequest request) {

        List<School> schoolList = schoolService.findAll();

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(SchoolResource.toResource((schoolList != null) ? schoolList.get(0) : null));
        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/announcement", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findPublicAnnoucment(HttpServletRequest request,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "size", required = false) Integer size) {

        AnnouncementType announcementType = announcementTypeService.findAnnouncementTypeByCode("PUBLIC");

        Status status = statusService.findStatusByCode("ACTIVE");

        Announcement announcementSearchCriteria = new Announcement();
        announcementSearchCriteria.setAnnouncementType(announcementType);
        announcementSearchCriteria.setExpireDate(Utils.getCurrentDateTimeJordan());
        announcementSearchCriteria.setStatus(status);

        Page<Announcement> announcementList = announcementService.findAll(announcementSearchCriteria, page, size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(announcementList.getTotalElements(), announcementList.getTotalPages(), AnnouncementResource.toResource(announcementList.getContent())));
        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }


    @RequestMapping(value = "/announcement/findById", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findById(HttpServletRequest request,
                                                @RequestParam(value = "id", required = false) Long id) {

        Announcement announcementSearchCriteria = new Announcement();
        announcementSearchCriteria.setId(id);

        Page<Announcement> announcementPage = announcementService.findAll(announcementSearchCriteria, null, null);

        PageResource pageResource = new PageResource();
        pageResource.setCount(announcementPage.getTotalElements());
        pageResource.setNumberOfPages(announcementPage.getTotalPages());
        pageResource.setPageList(AnnouncementResource.toResource(announcementPage.getContent()));

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(pageResource);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

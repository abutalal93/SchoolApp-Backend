package com.decoders.school.controller;

import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.School;
import com.decoders.school.entities.Status;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.SchoolResource;
import com.decoders.school.service.AnnouncementService;
import com.decoders.school.service.AnnouncementTypeService;
import com.decoders.school.service.SchoolService;
import com.decoders.school.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<MessageBody> findPublicAnnoucment(HttpServletRequest request) {

        AnnouncementType announcementType = announcementTypeService.findAnnouncementTypeByCode("PUBLIC");

        List<Announcement> announcementList = announcementService.findAnnouncement(announcementType, statusService.findStatusByCode("ACTIVE"));

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(AnnouncementResource.toResource(announcementList));
        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

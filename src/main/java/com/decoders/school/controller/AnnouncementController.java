package com.decoders.school.controller;

import com.decoders.school.Utils.NotificationMessage;
import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.*;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AnnouncementImageResource;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.resource.StudentResource;
import com.decoders.school.security.JwtTokenProvider;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentAnnouncementService studentAnnouncementService;


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

        Announcement announcement = announcementResource.toAnnouncement();

        List<Student> studentList = null;

        switch (announcementResource.getAnnouncementTypeCode()) {
            case "PUBLIC":
                announcement = announcementService.save(announcementResource.toAnnouncement());

                List<AnnouncementImage> announcementImageList = new ArrayList<>(announcementResource.getImageList().size());

                for (AnnouncementImageResource announcementImageResource : announcementResource.getImageList()) {
                    announcementImageResource.setUrl(Utils.archiveFile(environment.getProperty("attachment_url"), environment.getProperty("attachment_path"), announcementImageResource.getUrl()));
                    AnnouncementImage announcementImage = announcementImageResource.toAnnouncementImage();
                    announcementImage.setAnnouncement(announcement);
                    announcementImage = announcementImageService.save(announcementImage);
                    announcementImageList.add(announcementImage);
                }

                announcement.setAnnouncementImageList(announcementImageList);
                break;
            case "PRIVATE":
                if (announcementResource.getStudentList() == null
                        || announcementResource.getStudentList().isEmpty()) {
                    throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
                }

                announcement = announcementService.save(announcementResource.toAnnouncement());

                announcementImageList = new ArrayList<>(announcementResource.getImageList().size());

                for (AnnouncementImageResource announcementImageResource : announcementResource.getImageList()) {
                    announcementImageResource.setUrl(Utils.archiveFile(environment.getProperty("attachment_url"), environment.getProperty("attachment_path"), announcementImageResource.getUrl()));
                    AnnouncementImage announcementImage = announcementImageResource.toAnnouncementImage();
                    announcementImage.setAnnouncement(announcement);
                    announcementImage = announcementImageService.save(announcementImage);
                    announcementImageList.add(announcementImage);
                }

                announcement.setAnnouncementImageList(announcementImageList);

                studentList = new ArrayList<>(announcementResource.getStudentList().size());

                for (StudentResource studentResource : announcementResource.getStudentList()) {
                    Student student = studentService.findStudentById(studentResource.getId());
                    studentList.add(student);

                    StudentAnnouncement studentAnnouncement = new StudentAnnouncement();
                    studentAnnouncement.setAnnouncement(announcement);
                    studentAnnouncement.setStudent(student);
                    studentAnnouncement = studentAnnouncementService.createStudentAnnoucment(studentAnnouncement);
                }

                break;
            case "HOMEWORK":
                if (announcementResource.getStudentList() == null
                        || announcementResource.getStudentList().isEmpty()) {
                    throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
                }

                announcement = announcementService.save(announcementResource.toAnnouncement());

                announcementImageList = new ArrayList<>(announcementResource.getImageList().size());

                for (AnnouncementImageResource announcementImageResource : announcementResource.getImageList()) {
                    announcementImageResource.setUrl(Utils.archiveFile(environment.getProperty("attachment_url"), environment.getProperty("attachment_path"), announcementImageResource.getUrl()));
                    AnnouncementImage announcementImage = announcementImageResource.toAnnouncementImage();
                    announcementImage.setAnnouncement(announcement);
                    announcementImage = announcementImageService.save(announcementImage);
                    announcementImageList.add(announcementImage);
                }

                announcement.setAnnouncementImageList(announcementImageList);

                studentList = new ArrayList<>(announcementResource.getStudentList().size());

                for (StudentResource studentResource : announcementResource.getStudentList()) {
                    Student student = studentService.findStudentById(studentResource.getId());
                    studentList.add(student);

                    StudentAnnouncement studentAnnouncement = new StudentAnnouncement();
                    studentAnnouncement.setAnnouncement(announcement);
                    studentAnnouncement.setStudent(student);
                    studentAnnouncement = studentAnnouncementService.createStudentAnnoucment(studentAnnouncement);
                }
                break;
            default:
                throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
        }

        MessageBody messageBody = MessageBody.getInstance();
        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<MessageBody> deleteClass(HttpServletRequest request, @RequestBody AnnouncementResource announcementResource) {

        if (announcementResource.getId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Announcement announcement = announcementResource.toAnnouncement();

        announcementService.delete(announcement);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAll(HttpServletRequest request,
                                               @RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "type", required = false) String type,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {

        AnnouncementType announcementType = announcementTypeService.findAnnouncementTypeByCode(type);

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setAnnouncementType(announcementType);
        announcement.setStatus(statusService.findStatusByCode("ACTIVE"));

        Page<Announcement> announcementList = announcementService.findAll(announcement, page, size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(announcementList.getTotalElements(), announcementList.getTotalPages(), AnnouncementResource.toResource(announcementList.getContent())));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }


    @RequestMapping(value = "/findByStudent", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findByStudent(HttpServletRequest request,
                                                     @RequestParam(value = "studentId", required = false) Long studentId,
                                                     @RequestParam(value = "type", required = false) String type,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {

        AnnouncementType announcementType = announcementTypeService.findAnnouncementTypeByCode(type);

        Announcement announcement = new Announcement();
        announcement.setAnnouncementType(announcementType);
        announcement.setStatus(statusService.findStatusByCode("ACTIVE"));
        announcement.setExpireDate(LocalDateTime.now());

        Student student = studentService.findStudentById(studentId);

        StudentAnnouncement studentAnnouncementSearchCriteria = new StudentAnnouncement();
        studentAnnouncementSearchCriteria.setAnnouncement(announcement);
        studentAnnouncementSearchCriteria.setStudent(student);

        Page<StudentAnnouncement> studentAnnouncementPage = studentAnnouncementService.findAll(studentAnnouncementSearchCriteria, page, size);

        PageResource pageResource = new PageResource();
        pageResource.setCount(studentAnnouncementPage.getTotalElements());
        pageResource.setNumberOfPages(studentAnnouncementPage.getTotalPages());
        pageResource.setPageList(AnnouncementResource.toResourceStudentAnnouncement(studentAnnouncementPage.getContent()));

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(pageResource);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

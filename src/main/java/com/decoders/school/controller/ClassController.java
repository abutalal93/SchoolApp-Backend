package com.decoders.school.controller;

import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Class;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.ClassResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private AcademicYearService academicYearService;


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAllClass(HttpServletRequest request,
                                                    @RequestParam(name = "id", required = false) Long classId,
                                                    @RequestParam(name = "name", required = false) String className,
                                                    @RequestParam(name = "academicYearId", required = false) Long academicYearId,
                                                    @RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "size", required = false) Integer size) {


        Class classSearchCriteria = new Class();
        classSearchCriteria.setId(classId);
        classSearchCriteria.setName(className);
        classSearchCriteria.setAcademicYear(academicYearId == null || academicYearId == 0 ? null : academicYearService.findAcademicYearById(academicYearId));
        Page<Class> classPage = classService.findAll(classSearchCriteria, page , size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(classPage.getTotalElements(), classPage.getTotalPages(), ClassResource.toResource(classPage.getContent())));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findClassById(HttpServletRequest request, @RequestParam(name = "id", required = true) Long classId) {


        Class clasS = classService.findClassById(classId);

        if (clasS == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(ClassResource.toResource(clasS));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> createClass(HttpServletRequest request, @RequestBody ClassResource classResource) {

        if (classResource.getName() == null
                || classResource.getAcademicYearId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Class clasS = classResource.toClass();

        clasS = classService.save(clasS);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(ClassResource.toResource(clasS));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<MessageBody> updateClass(HttpServletRequest request, @RequestBody ClassResource classResource) {

        if (classResource.getId() == null
                || classResource.getName() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Class classS = classResource.toClass();

        classS = classService.update(classS);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(ClassResource.toResource(classS));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<MessageBody> deleteClass(HttpServletRequest request, @RequestBody ClassResource classResource) {

        if (classResource.getId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Class classS = classResource.toClass();

        classService.delete(classS);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

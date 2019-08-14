package com.decoders.school.controller;

import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.SectionResource;
import com.decoders.school.service.ClassService;
import com.decoders.school.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ClassService classService;


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAllSection(HttpServletRequest request,@RequestParam(name = "classId" , required = false) Long classId) {

        Class clasS = null;

        List<Section> sectionList = null;

        if(classId != null && classId !=0){
            clasS = classService.findClassById(classId);
            sectionList = sectionService.findAll(clasS);
        }else{
            sectionList = sectionService.findAll();
        }

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(SectionResource.toResource(sectionList));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findSectionById(HttpServletRequest request,@RequestParam(name = "id" , required = true) Long sectionId) {


        Section section = sectionService.findSectionById(sectionId);

        if(section == null){
            throw new ResourceException(HttpStatus.NOT_FOUND, "section_not_found");
        }

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(SectionResource.toResource(section));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> createSection(HttpServletRequest request, @RequestBody SectionResource sectionResource) {

        if (sectionResource.getName() == null
                || sectionResource.getClassId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Section section = sectionResource.toSection();

        section = sectionService.save(section);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(SectionResource.toResource(section));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<MessageBody> updateSection(HttpServletRequest request, @RequestBody SectionResource sectionResource) {

        if (sectionResource.getId() == null
                || sectionResource.getName() == null
                || sectionResource.getClassId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Section section = sectionResource.toSection();

        section = sectionService.update(section);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(SectionResource.toResource(section));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<MessageBody> deleteSection(HttpServletRequest request, @RequestBody SectionResource sectionResource) {

        if (sectionResource.getId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Section section = sectionResource.toSection();

        sectionService.delete(section);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

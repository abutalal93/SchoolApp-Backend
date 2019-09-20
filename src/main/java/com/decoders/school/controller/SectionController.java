package com.decoders.school.controller;

import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AcademicYearResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.resource.SectionResource;
import com.decoders.school.service.AcademicYearService;
import com.decoders.school.service.ClassService;
import com.decoders.school.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private AcademicYearService academicYearService;


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAllSection(HttpServletRequest request,
                                                      @RequestParam(name = "id", required = false) Long id,
                                                      @RequestParam(name = "name", required = false) String sectionName,
                                                      @RequestParam(name = "classId", required = false) Long classId,
                                                      @RequestParam(name = "academicYearId", required = false) Long academicYearId,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size) {

        Section sectionSearchCriteria = new Section();
        sectionSearchCriteria.setId(id);
        sectionSearchCriteria.setName(sectionName);
        sectionSearchCriteria.setClasS(classId == null || classId == 0 ? null : classService.findClassById(classId));
        sectionSearchCriteria.setAcademicYear(academicYearId == null || academicYearId == 0 ? null : academicYearService.findAcademicYearById(academicYearId));
        Page<Section> sectionPage = sectionService.findAll(sectionSearchCriteria,page,size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(sectionPage.getTotalElements(), sectionPage.getTotalPages(), SectionResource.toResource(sectionPage.getContent())));

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
                || sectionResource.getClassId() == null
                || sectionResource.getAcademicYearId() == null) {
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
                || sectionResource.getClassId() == null
                || sectionResource.getAcademicYearId() == null) {
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

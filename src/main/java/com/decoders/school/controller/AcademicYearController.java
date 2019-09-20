package com.decoders.school.controller;

import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.AcademicYearResource;
import com.decoders.school.resource.AnnouncementResource;
import com.decoders.school.resource.ClassResource;
import com.decoders.school.resource.PageResource;
import com.decoders.school.service.AcademicYearService;
import com.decoders.school.service.ClassService;
import com.decoders.school.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/AcademicYear")
public class AcademicYearController {

    @Autowired
    private AcademicYearService academicYearService;

    @Autowired
    private StatusService statusService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAllAcademicYear(HttpServletRequest request,
                                                           @RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "size", required = false) Integer size) {


        Page<AcademicYear> academicYearPage = academicYearService.findAll(page , size);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(new PageResource(academicYearPage.getTotalElements(), academicYearPage.getTotalPages(), AcademicYearResource.toResource(academicYearPage.getContent())));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAcademicYearById(HttpServletRequest request,@RequestParam(name = "id" , required = true) Long acdId) {


        AcademicYear academicYear = academicYearService.findAcademicYearById(acdId);

        if(academicYear == null){
            throw new ResourceException(HttpStatus.NOT_FOUND, "academicYear_not_found");
        }

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(AcademicYearResource.toResource(academicYear));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> createAcademicYear(HttpServletRequest request, @RequestBody AcademicYearResource academicYearResource) {

        if (academicYearResource.getStartDate() == null
                || academicYearResource.getEndDate() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        if(academicYearResource.getEndDate().isBefore(academicYearResource.getStartDate())){
            throw new ResourceException(HttpStatus.BAD_REQUEST, "end_befor_start");
        }

        AcademicYear academicYear = academicYearResource.toAcademicYear();

        academicYear.setStatus(statusService.findStatusByCode("ACTIVE"));

        academicYear = academicYearService.save(academicYear);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(AcademicYearResource.toResource(academicYear));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<MessageBody> updateAcademicYear(HttpServletRequest request, @RequestBody AcademicYearResource academicYearResource) {

        if (academicYearResource.getId() == null
                || academicYearResource.getStartDate() == null
                || academicYearResource.getEndDate() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        if(academicYearResource.getEndDate().isBefore(academicYearResource.getStartDate())){
            throw new ResourceException(HttpStatus.BAD_REQUEST, "end_befor_start");
        }

        AcademicYear academicYear = academicYearResource.toAcademicYear();

        academicYear = academicYearService.update(academicYear);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(AcademicYearResource.toResource(academicYear));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<MessageBody> deleteAcademicYear(HttpServletRequest request, @RequestBody AcademicYearResource academicYearResource) {

        if (academicYearResource.getId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        AcademicYear academicYear = academicYearResource.toAcademicYear();

        academicYearService.delete(academicYear);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

}

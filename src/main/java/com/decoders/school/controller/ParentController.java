package com.decoders.school.controller;

import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.School;
import com.decoders.school.entities.Student;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.OtpResource;
import com.decoders.school.resource.SchoolResource;
import com.decoders.school.resource.StudentResource;
import com.decoders.school.security.JwtTokenProvider;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private OtpService otpService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ParentService parentService;

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> loginSchoolAdmin(HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);

        String parentMobile = jwtTokenProvider.getUsername(token);

        List<Student> studentList = studentService.findStudent(parentMobile, statusService.findStatusByCode("ACTIVE"));

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(StudentResource.toResource(studentList));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

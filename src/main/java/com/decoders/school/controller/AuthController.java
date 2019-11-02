package com.decoders.school.controller;

import com.decoders.school.Utils.NotificationMessage;
import com.decoders.school.Utils.PushNotificationHandler;
import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.School;
import com.decoders.school.entities.Student;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.OtpResource;
import com.decoders.school.resource.ParentResource;
import com.decoders.school.resource.SchoolResource;
import com.decoders.school.security.JwtTokenProvider;
import com.decoders.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

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

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> loginSchoolAdmin(HttpServletRequest request, @RequestBody SchoolResource schoolResource) {

        if (schoolResource.getUsername() == null
                || schoolResource.getPassword() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
        }

        School school = schoolService.findSchoolByUsername(schoolResource.getUsername());

        if (school == null) {
            throw new ResourceException(HttpStatus.UNAUTHORIZED, "invalid_login");
        }

        if (!school.getPassword().equals(schoolResource.getPassword())) {
            throw new ResourceException(HttpStatus.UNAUTHORIZED, "invalid_login");
        }

        String token = jwtTokenProvider.createToken(school.getUsername());

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(token);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/otp", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> sendOtp(HttpServletRequest request, @RequestBody OtpResource otpResource) {

        if (otpResource.getMobile() == null
                || otpResource.getCountryCode() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
        }

        String validMobile = Utils.standardPhoneFormat(otpResource.getCountryCode(), otpResource.getMobile());

        if (validMobile == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "mobile_invalid");
        }

//        List<Student> studentList = studentService.findStudent(validMobile, statusService.findStatusByCode("ACTIVE"));
//
//        if (studentList == null || studentList.isEmpty()) {
//            throw new ResourceException(HttpStatus.BAD_REQUEST, "no_student_found");
//        }

        otpResource.setMobile(validMobile);
        Otp otp = otpService.create(otpResource.toOtp());

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(OtpResource.toResource(otp));

        new Thread() {
            public void run() {
                System.out.println("push thread start");
                NotificationMessage notificationMessage = new NotificationMessage();
                notificationMessage.setMobileNumber(validMobile);
                notificationMessage.setBody("رمز التحقق الخاص بك هو: " + otp.getCode() + "\n" +
                        "أغلق هذه الرسالة وادخل في التطبيق لتنشيط حسابك");
                notificationMessage.setApplicationContext(applicationContext);
                PushNotificationHandler.sendSms(notificationMessage);
            }
        }.start();

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> subscribeParent(HttpServletRequest request, @RequestBody OtpResource otpResource) {

        if (otpResource.getMobile() == null
                || otpResource.getCountryCode() == null
                || otpResource.getToken() == null
                || otpResource.getPlatform() == null
                || otpResource.getCode() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "request_null");
        }

        String validMobile = Utils.standardPhoneFormat(otpResource.getCountryCode(), otpResource.getMobile());

        if (validMobile == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "mobile_invalid");
        }

        Otp otp = otpService.findOtpByMobileAndStatus(validMobile, statusService.findStatusByCode("ACTIVE"));

        if (otp == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "otp_invalid");
        }

        if (otp.getExpireDate().isBefore(Utils.getCurrentDateTimeJordan())) {
            otpService.updateOtp(validMobile, statusService.findStatusByCode("INACTIVE"));
            throw new ResourceException(HttpStatus.BAD_REQUEST, "otp_invalid");
        }

        if (!otp.getCode().equals(otpResource.getCode())) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "otp_invalid");
        }

        otpService.updateOtp(validMobile, statusService.findStatusByCode("INACTIVE"));

        List<Student> studentList = studentService.findStudent(validMobile, statusService.findStatusByCode("ACTIVE"));

        if (studentList == null || studentList.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "no_student_found");
        }

        Parent parent = parentService.createOrUpdate(validMobile, otpResource.getPlatform(), otpResource.getToken());

        String token = jwtTokenProvider.createToken(parent.getMobile());

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(token);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

}

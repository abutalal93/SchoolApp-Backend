package com.decoders.school.controller;


import com.decoders.school.Utils.ExcelHandler;
import com.decoders.school.Utils.StudentExcelFile;
import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Student;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.StudentResource;
import com.decoders.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StudentService studentService;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> getExcelTemplate() throws IOException {

        File templateExcelFile = Utils.createExcelFile();

        String fileDate = Utils.encodeFileToBase64Binary(templateExcelFile.getPath());

        LocalDateTime currentDateTime = LocalDateTime.now();

        StudentExcelFile responseFile = new StudentExcelFile();
        responseFile.setName(currentDateTime.getYear() + "_" + currentDateTime.getMonthValue() + "_" + currentDateTime.getDayOfMonth() + "_" + currentDateTime.getHour() + "_" + currentDateTime.getMinute() + "_" + currentDateTime.getSecond()+"_"+templateExcelFile.getName());
        responseFile.setBase64(fileDate);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(responseFile);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> uploadExcelStudent(@RequestBody StudentExcelFile studentExcelFile) {

        if (studentExcelFile.getBase64() == null
                || studentExcelFile.getName() == null
                || studentExcelFile.getType() == null)
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");

        ExcelHandler excelHandler = new ExcelHandler(studentExcelFile.getBase64(), studentExcelFile.getName(), studentExcelFile.getType(), applicationContext);

        MessageBody messageBody = MessageBody.getInstance();

        if (excelHandler.getExcelErrorList().isEmpty()) {
            //insert list of student:

            studentService.saveAll(excelHandler.getStudentList());

            messageBody.setStatus("200");
            messageBody.setText("OK");
            messageBody.setBody(null);
            return new ResponseEntity<>(messageBody, HttpStatus.OK);
        } else {
            //
            messageBody.setStatus("400");
            messageBody.setText("Error");
            messageBody.setBody(excelHandler.getExcelErrorList());
            return new ResponseEntity<>(messageBody, HttpStatus.BAD_REQUEST);
        }

    }

}

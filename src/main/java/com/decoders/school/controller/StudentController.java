package com.decoders.school.controller;

import com.decoders.school.Utils.Utils;
import com.decoders.school.config.MessageBody;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Student;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.resource.StudentResource;
import com.decoders.school.service.AcademicYearService;
import com.decoders.school.service.ClassService;
import com.decoders.school.service.SectionService;
import com.decoders.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private AcademicYearService academicYearService;


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findAllStudent(HttpServletRequest request,
                                                      @RequestParam(name = "id", required = false) Long id,
                                                      @RequestParam(name = "name", required = false) String studentName,
                                                      @RequestParam(name = "sectionId", required = false) Long sectionId,
                                                      @RequestParam(name = "classId", required = false) Long classId,
                                                      @RequestParam(name = "academicYearId", required = false) Long academicYearId) {

        Student studentSearchCriteria = new Student();
        studentSearchCriteria.setId(id);
        studentSearchCriteria.setName(studentName);
        studentSearchCriteria.setSection(sectionId == null || sectionId == 0 ? ((classId == null || classId == 0) ? (null) : (new Section(classService.findClassById(classId)))) : sectionService.findSectionById(sectionId));
        studentSearchCriteria.setAcademicYear(academicYearId == null || academicYearId == 0 ? null : academicYearService.findAcademicYearById(academicYearId));

        List<Student> studentList = studentService.findAll(studentSearchCriteria);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(StudentResource.toResource(studentList));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<MessageBody> findStudentById(HttpServletRequest request, @RequestParam(name = "id", required = true) Long studentId) {


        Student student = studentService.findStudentById(studentId);

        if (student == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "student_not_found");
        }

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(StudentResource.toResource(student));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> createStudent(HttpServletRequest request, @RequestBody StudentResource studentResource) {

        if (studentResource.getName() == null
                || studentResource.getSectionId() == null
                || studentResource.getAcademicYearId() == null
//                || studentResource.getFatherMobile() == null
//                || studentResource.getMotherMobile() == null
                || studentResource.getNationalNumber() == null
                || studentResource.getGender() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Student student = studentResource.toStudent();

        if(studentResource.getFatherMobile() != null){
            String validMobileFather = Utils.standardPhoneFormat("+962", studentResource.getFatherMobile());

            if (validMobileFather == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "father_mobile_invalid");
            }
            student.setFatherMobile(validMobileFather);
        }

        if(studentResource.getMotherMobile() != null){
            String validMobileMother = Utils.standardPhoneFormat("+962", studentResource.getMotherMobile());

            if (validMobileMother == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "mother_mobile_invalid");
            }

            student.setMotherMobile(validMobileMother);
        }

        student = studentService.save(student);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(StudentResource.toResource(student));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<MessageBody> updateStudent(HttpServletRequest request, @RequestBody StudentResource studentResource) {

        if (studentResource.getId() == null
                || studentResource.getName() == null
                || studentResource.getSectionId() == null
                || studentResource.getAcademicYearId() == null
//                || studentResource.getFatherMobile() == null
//                || studentResource.getMotherMobile() == null
                || studentResource.getNationalNumber() == null
                || studentResource.getGender() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Student student = studentResource.toStudent();

        if(studentResource.getFatherMobile() != null){
            String validMobileFather = Utils.standardPhoneFormat("+962", studentResource.getFatherMobile());

            if (validMobileFather == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "father_mobile_invalid");
            }
            student.setFatherMobile(validMobileFather);
        }

        if(studentResource.getMotherMobile() != null){
            String validMobileMother = Utils.standardPhoneFormat("+962", studentResource.getMotherMobile());

            if (validMobileMother == null) {
                throw new ResourceException(HttpStatus.BAD_REQUEST, "mother_mobile_invalid");
            }

            student.setMotherMobile(validMobileMother);
        }

        student = studentService.update(student);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(StudentResource.toResource(student));

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<MessageBody> deleteStudent(HttpServletRequest request, @RequestBody StudentResource studentResource) {

        if (studentResource.getId() == null) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Student student = studentResource.toStudent();

        studentService.delete(student);

        MessageBody messageBody = MessageBody.getInstance();

        messageBody.setStatus("200");
        messageBody.setText("OK");
        messageBody.setBody(null);

        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }
}

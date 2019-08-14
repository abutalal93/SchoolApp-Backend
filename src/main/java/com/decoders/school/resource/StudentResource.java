package com.decoders.school.resource;

import com.decoders.school.entities.Student;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentResource {


    private Long id;
    private String name;
    private String gender;
    private String nationalNumber;
    private LocalDateTime createDate;
    private String image;
    private String fatherMobile;
    private String motherMobile;
    private Long statusId;
    private String statusCode;
    private Long sectionId;
    private String sectionName;
    private Long academicYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFatherMobile() {
        return fatherMobile;
    }

    public void setFatherMobile(String fatherMobile) {
        this.fatherMobile = fatherMobile;
    }

    public String getMotherMobile() {
        return motherMobile;
    }

    public void setMotherMobile(String motherMobile) {
        this.motherMobile = motherMobile;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public static StudentResource toResource(Student student) {
        StudentResource studentResource = new StudentResource();

        studentResource.setId(student.getId());
        studentResource.setCreateDate(student.getCreateDate());
        studentResource.setFatherMobile(student.getFatherMobile());
        studentResource.setMotherMobile(student.getMotherMobile());
        studentResource.setName(student.getName());
        studentResource.setNationalNumber(student.getNationalNumber());
        studentResource.setGender(student.getGender());
        studentResource.setImage(student.getImage());

        if (student.getStatus() != null) {
            studentResource.setStatusId(student.getStatus().getId());
            studentResource.setStatusCode(student.getStatus().getCode());
        }

        if (student.getSection() != null) {
            studentResource.setSectionId(student.getSection().getId());
            studentResource.setSectionName(student.getSection().getName());
        }

        if (student.getAcademicYear() != null) {
            studentResource.setAcademicYearId(student.getAcademicYear().getId());
        }

        return studentResource;
    }

    public static List<StudentResource> toResource(List<Student> studentList) {
        List<StudentResource> studentResourceList = new ArrayList<>();
        studentList.forEach(student -> {
            StudentResource studentResource = toResource(student);
            studentResourceList.add(studentResource);
        });
        return studentResourceList;
    }

    public Student toStudent() {
        Student student = new Student();

        student.setId(this.id);
        student.setCreateDate(this.createDate);
        student.setNationalNumber(this.nationalNumber);
        student.setName(this.name);
        student.setNationalNumber(this.nationalNumber);
        student.setGender(this.gender);
        student.setFatherMobile(this.fatherMobile);
        student.setMotherMobile(this.motherMobile);

        if (statusId == null) {
            Status status = new Status();
            status.setId(this.id);
            student.setStatus(status);
        }
        return student;
    }

}

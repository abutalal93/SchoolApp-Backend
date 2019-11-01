package com.decoders.school.resource;

import com.decoders.school.entities.*;

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
    private String academicYearAlias;
    private Long classId;
    private String className;

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

    public String getAcademicYearAlias() {
        return academicYearAlias;
    }

    public void setAcademicYearAlias(String academicYearAlias) {
        this.academicYearAlias = academicYearAlias;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

            if(student.getSection().getClasS() != null){
                studentResource.setClassId(student.getSection().getClasS().getId());
                studentResource.setClassName(student.getSection().getClasS().getName());
            }
        }

        if (student.getAcademicYear() != null) {
            studentResource.setAcademicYearId(student.getAcademicYear().getId());
            studentResource.setAcademicYearAlias(student.getAcademicYear().getAlias());
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

    public static List<StudentResource> toStudentResource(List<StudentAnnouncement> studentAnnouncementList) {
        List<StudentResource> studentResourceList = new ArrayList<>();
        studentAnnouncementList.forEach(studentAnnouncement -> {
            StudentResource studentResource = toResource(studentAnnouncement.getStudent());
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

        if (statusId != null) {
            Status status = new Status();
            status.setId(this.id);
            student.setStatus(status);
        }

        if(this.academicYearId != null){
            AcademicYear academicYear = new AcademicYear();
            academicYear.setId(this.academicYearId);
            student.setAcademicYear(academicYear);
        }

        if(sectionId != null){
            Section section = new Section();
            section.setId(this.sectionId);
            student.setSection(section);
        }
        return student;
    }

}

package com.decoders.school.resource;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClassResource {


    private Long id;

    private String name;

    private Long statusId;

    private String statusCode;

    private Long academicYearId;

    private String academicYearAlias;

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

    public static ClassResource toResource(Class clasS)
    {
        ClassResource classResource = new ClassResource();

        classResource.setId(clasS.getId());
        classResource.setName(clasS.getName());

        if(clasS.getStatus() != null) {
            classResource.setStatusId(clasS.getStatus().getId());
            classResource.setStatusCode(clasS.getStatus().getCode());
        }

        if(clasS.getAcademicYear() != null) {
            classResource.setAcademicYearId(clasS.getAcademicYear().getId());
            classResource.setAcademicYearAlias(clasS.getAcademicYear().getAlias());
        }

        return classResource;
    }

    public static List<ClassResource> toResource(List<Class> classList){
        List<ClassResource> classResourceList = new ArrayList<>();
        classList.forEach(clasS -> {
            ClassResource classResource = toResource(clasS);
            classResourceList.add(classResource);
        });
        return classResourceList;
    }

    public Class toClass(){

        Class clasS = new Class();
        clasS.setId(this.id);
        clasS.setName(this.name);

        if(this.statusId != null){
            Status status = new Status();
            status.setId(this.statusId);
            clasS.setStatus(status);
        }

        if(this.academicYearId != null){
            AcademicYear academicYear = new AcademicYear();
            academicYear.setId(this.academicYearId);
            clasS.setAcademicYear(academicYear);
        }

        return clasS;

    }

}

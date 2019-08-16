package com.decoders.school.resource;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Status;

import java.util.ArrayList;
import java.util.List;

public class SectionResource {


    private Long id;

    private String name;

    private Long classId;

    private String className;

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

    public String getAcademicYearAlias() {
        return academicYearAlias;
    }

    public void setAcademicYearAlias(String academicYearAlias) {
        this.academicYearAlias = academicYearAlias;
    }

    public static SectionResource toResource(Section section)
    {
        SectionResource sectionResource = new SectionResource();

        sectionResource.setId(section.getId());
        sectionResource.setName(section.getName());

        if(section.getStatus() != null) {
            sectionResource.setStatusId(section.getStatus().getId());
            sectionResource.setStatusCode(section.getStatus().getCode());
        }

        if(section.getAcademicYear() != null) {
            sectionResource.setAcademicYearId(section.getAcademicYear().getId());
            sectionResource.setAcademicYearAlias(section.getAcademicYear().getAlias());
        }

        if(section.getClasS() != null) {
            sectionResource.setClassId(section.getClasS().getId());
            sectionResource.setClassName(section.getClasS().getName());
        }

        return sectionResource;
    }

    public static List<SectionResource> toResource(List<Section> sectionList){
        List<SectionResource> sectionResourceList = new ArrayList<>();
        sectionList.forEach(section -> {
            SectionResource sectionResource = toResource(section);
            sectionResourceList.add(sectionResource);
        });
        return sectionResourceList;
    }

    public Section toSection(){

        Section section = new Section();
        section.setId(this.id);
        section.setName(this.name);

        if(this.statusId != null){
            Status status = new Status();
            status.setId(this.statusId);
            section.setStatus(status);
        }

        if(this.academicYearId != null){
            AcademicYear academicYear = new AcademicYear();
            academicYear.setId(this.academicYearId);
            section.setAcademicYear(academicYear);
        }

        if(this.classId != null){
            Class clasS = new Class();
            clasS.setId(this.classId);
            section.setClasS(clasS);
        }

        return section;

    }

}

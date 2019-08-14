package com.decoders.school.resource;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AcademicYearResource {

    private Long id;

    private String alias;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long statusId;
    
    private String statusCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public static AcademicYearResource toResource(AcademicYear academicYear)
    {
        AcademicYearResource academicYearResource = new AcademicYearResource();

        academicYearResource.setId(academicYear.getId());
        academicYearResource.setStartDate(academicYear.getStartDate());
        academicYearResource.setEndDate(academicYear.getEndDate());
        academicYearResource.setAlias(academicYear.getAlias());

        if(academicYear.getStatus() != null) {
            academicYearResource.setStatusId(academicYear.getStatus().getId());
            academicYearResource.setStatusCode(academicYear.getStatus().getCode());
        }

        return academicYearResource;
    }

    public static List<AcademicYearResource> toResource(List<AcademicYear> academicYearList){
        List<AcademicYearResource> academicYearResourceList = new ArrayList<>();
        academicYearList.forEach(academicYear -> {
            AcademicYearResource academicYearResource = toResource(academicYear);
            academicYearResourceList.add(academicYearResource);
        });
        return academicYearResourceList;
    }

    public AcademicYear toAcademicYear(){
        AcademicYear academicYear = new AcademicYear();

        academicYear.setId(this.id);
        academicYear.setAlias(this.alias);
        academicYear.setStartDate(this.startDate);
        academicYear.setEndDate(this.endDate);

        if(statusId == null){
            Status status = new Status();
            status.setId(this.id);
            academicYear.setStatus(status);
        }
        return academicYear;
    }

}

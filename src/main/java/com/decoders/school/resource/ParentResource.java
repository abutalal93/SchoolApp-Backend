package com.decoders.school.resource;

import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParentResource {


    private Long id;

    private String name;

    private String nationalNumber;

    private LocalDateTime createDate;

    private String mobile;

    private String platform;

    private String token;

    private Long statusId;

    private String statusCode;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public static ParentResource toResource(Parent parent) {
        ParentResource parentResource = new ParentResource();

        parentResource.setId(parent.getId());
        parentResource.setCreateDate(parent.getCreateDate());
        parentResource.setMobile(parent.getMobile());
        parentResource.setName(parent.getName());
        parentResource.setNationalNumber(parent.getNationalNumber());
        parentResource.setPlatform(parent.getPlatform());
        parentResource.setToken(parent.getToken());

        if (parent.getStatus() != null) {
            parentResource.setStatusId(parent.getStatus().getId());
            parentResource.setStatusCode(parent.getStatus().getCode());
        }

        return parentResource;
    }

    public static List<ParentResource> toResource(List<Parent> parentList) {
        List<ParentResource> parentResourceList = new ArrayList<>();
        parentList.forEach(parent -> {
            ParentResource parentResource = toResource(parent);
            parentResourceList.add(parentResource);
        });
        return parentResourceList;
    }

    public Parent toParent() {
        Parent parent = new Parent();

        parent.setId(this.id);
        parent.setCreateDate(this.createDate);
        parent.setMobile(this.mobile);
        parent.setName(this.name);
        parent.setNationalNumber(this.nationalNumber);
        parent.setPlatform(this.platform);
        parent.setToken(this.token);

        if (statusId == null) {
            Status status = new Status();
            status.setId(this.id);
            parent.setStatus(status);
        }
        return parent;
    }

}

package com.decoders.school.resource;

import com.decoders.school.entities.School;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SchoolResource {


    private Long id;

    private String name;

    private String code;

    private LocalDateTime createDate;

    private LocalDateTime expireDate;

    private String username;

    private String password;

    private String image;

    private String email;

    private String mobile;

    private String longitude;

    private String latitude;

    private String facebookUrl;

    private String twitterUrl;

    private String chairmanMessage;
    
    private Long statusId;

    private String statusCode;

    private String aboutText;

    private String aboutImage;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getChairmanMessage() {
        return chairmanMessage;
    }

    public void setChairmanMessage(String chairmanMessage) {
        this.chairmanMessage = chairmanMessage;
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

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getAboutImage() {
        return aboutImage;
    }

    public void setAboutImage(String aboutImage) {
        this.aboutImage = aboutImage;
    }

    public static SchoolResource toResource(School school)
    {
        SchoolResource schoolResource = new SchoolResource();

        schoolResource.setId(school.getId());
        schoolResource.setCreateDate(school.getCreateDate());
        schoolResource.setExpireDate(school.getExpireDate());
        schoolResource.setImage(school.getImage());
        schoolResource.setEmail(school.getEmail());
        schoolResource.setMobile(school.getMobile());
        schoolResource.setName(school.getName());
        schoolResource.setChairmanMessage(school.getChairmanMessage());
        schoolResource.setCode(school.getCode());
        schoolResource.setFacebookUrl(school.getFacebookUrl());
        schoolResource.setTwitterUrl(school.getTwitterUrl());
        schoolResource.setLatitude(school.getLatitude());
        schoolResource.setLongitude(school.getLongitude());
        schoolResource.setAboutImage(school.getAboutImage());
        schoolResource.setAboutText(school.getAboutText());

        if(school.getStatus() != null) {
            schoolResource.setStatusId(school.getStatus().getId());
            schoolResource.setStatusCode(school.getStatus().getCode());
        }

        return schoolResource;
    }

    public static List<SchoolResource> toResource(List<School> schoolList){
        List<SchoolResource> schoolResourceList = new ArrayList<>();
        schoolList.forEach(school -> {
            SchoolResource schoolResource = toResource(school);
            schoolResourceList.add(schoolResource);
        });
        return schoolResourceList;
    }

}

package com.decoders.school.resource;


import com.decoders.school.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementResource {

    private Long id;

    private String title;

    private String text;

    private LocalDateTime createDate;

    private LocalDateTime expireDate;

    private List<AnnouncementImageResource> imageList;

    private List<StudentResource> studentList;

    private Long statusId;

    private String statusCode;

    private Long announcementTypeId;

    private String announcementTypeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public List<AnnouncementImageResource> getImageList() {
        return imageList;
    }

    public void setImageList(List<AnnouncementImageResource> imageList) {
        this.imageList = imageList;
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

    public Long getAnnouncementTypeId() {
        return announcementTypeId;
    }

    public void setAnnouncementTypeId(Long announcementTypeId) {
        this.announcementTypeId = announcementTypeId;
    }

    public String getAnnouncementTypeCode() {
        return announcementTypeCode;
    }

    public void setAnnouncementTypeCode(String announcementTypeCode) {
        this.announcementTypeCode = announcementTypeCode;
    }

    public List<StudentResource> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentResource> studentList) {
        this.studentList = studentList;
    }

    public static AnnouncementResource toResource(Announcement annoucment)
    {
        AnnouncementResource annoucmentResource = new AnnouncementResource();

        annoucmentResource.setId(annoucment.getId());
        annoucmentResource.setCreateDate(annoucment.getCreateDate());
        annoucmentResource.setExpireDate(annoucment.getExpireDate());
        annoucmentResource.setTitle(annoucment.getTitle());
        annoucmentResource.setText(annoucment.getText());

        if(annoucment.getStatus() != null) {
            annoucmentResource.setStatusId(annoucment.getStatus().getId());
            annoucmentResource.setStatusCode(annoucment.getStatus().getCode());
        }

        if(annoucment.getAnnouncementType() != null) {
            annoucmentResource.setAnnouncementTypeId(annoucment.getAnnouncementType().getId());
            annoucmentResource.setAnnouncementTypeCode(annoucment.getAnnouncementType().getCode());
        }

        if(annoucment.getAnnouncementImageList() != null) {
            annoucmentResource.setImageList(AnnouncementImageResource.toResource(annoucment.getAnnouncementImageList()));
        }

        return annoucmentResource;
    }

    public static List<AnnouncementResource> toResource(List<Announcement> annoucmentList){
        List<AnnouncementResource> annoucmentResourceList = new ArrayList<>();
        annoucmentList.forEach(annoucment -> {
            AnnouncementResource annoucmentResource = toResource(annoucment);
            annoucmentResourceList.add(annoucmentResource);
        });
        return annoucmentResourceList;
    }

    public static List<AnnouncementResource> toResourceStudentAnnouncement(List<StudentAnnouncement> studentAnnouncementList){
        List<AnnouncementResource> annoucmentResourceList = new ArrayList<>();
        studentAnnouncementList.forEach(studentAnnouncement -> {
            AnnouncementResource annoucmentResource = toResource(studentAnnouncement.getAnnouncement());
            annoucmentResourceList.add(annoucmentResource);
        });
        return annoucmentResourceList;
    }

    public Announcement toAnnouncement(){
        Announcement announcement = new Announcement();

        announcement.setId(this.id);
        announcement.setCreateDate(this.createDate);
        announcement.setExpireDate(this.expireDate);
        announcement.setText(this.text);
        announcement.setTitle(this.title);

        if (announcementTypeId != null || announcementTypeCode !=null){
            AnnouncementType announcementType = new AnnouncementType();
            announcementType.setId(this.announcementTypeId);
            announcementType.setCode(this.announcementTypeCode);
            announcement.setAnnouncementType(announcementType);
        }

        if (statusId != null || statusCode !=null){
            Status status = new Status();
            status.setId(this.statusId);
            status.setCode(this.statusCode);
            announcement.setStatus(status);
        }

        return announcement;
    }


}

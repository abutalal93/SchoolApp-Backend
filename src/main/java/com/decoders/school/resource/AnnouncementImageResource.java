package com.decoders.school.resource;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementImageResource {
    
    private Long id;

    private String url;
    
    private Long announcementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public static AnnouncementImageResource toResource(AnnouncementImage announcementImage)
    {
        AnnouncementImageResource announcementImageResource = new AnnouncementImageResource();

        announcementImageResource.setId(announcementImage.getId());
        announcementImageResource.setUrl(announcementImage.getUrl());

        if(announcementImage.getAnnouncement() != null) {
            announcementImageResource.setAnnouncementId(announcementImage.getAnnouncement().getId());
        }

        return announcementImageResource;
    }

    public static List<AnnouncementImageResource> toResource(List<AnnouncementImage> announcementImageList){
        List<AnnouncementImageResource> announcementImageResourceList = new ArrayList<>();
        announcementImageList.forEach(announcementImage -> {
            AnnouncementImageResource announcementImageResource = toResource(announcementImage);
            announcementImageResourceList.add(announcementImageResource);
        });
        return announcementImageResourceList;
    }

    public AnnouncementImage toAnnouncementImage(){

        AnnouncementImage announcementImage = new AnnouncementImage();

        announcementImage.setId(this.id);
        announcementImage.setUrl(this.url);

        if(announcementId != null){
            Announcement announcement = new Announcement();
            announcement.setId(this.announcementId);
            announcementImage.setAnnouncement(announcement);
        }

        return announcementImage;
    }

}

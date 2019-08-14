package com.decoders.school.service;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.resource.AnnouncementImageResource;

import java.util.List;

public interface AnnouncementImageService {
    public List<AnnouncementImage> findAnnouncementImageByAnnouncement(Announcement announcement);
    public AnnouncementImage save(AnnouncementImage announcementImage);
}

package com.decoders.school.repository;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementImageRepo extends CrudRepository<AnnouncementImage,Long> {
    public List<AnnouncementImage> findAnnouncementImageByAnnouncement(Announcement announcement);
    public AnnouncementImage save(AnnouncementImage announcementImage);
    public void deleteAllByAnnouncement(Announcement announcement);
}

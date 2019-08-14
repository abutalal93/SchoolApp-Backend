package com.decoders.school.repository;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepo extends CrudRepository<Announcement,Long> {
    public List<Announcement> findAll();
    public List<Announcement> findAnnouncementByAnnouncementTypeAndStatusOrderByCreateDateDesc(AnnouncementType announcementType, Status status);
    public Announcement save(Announcement announcement);
}

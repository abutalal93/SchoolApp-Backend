package com.decoders.school.repository;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepo extends CrudRepository<Announcement, Long> {
    public Page<Announcement> findAll(Pageable pageable);

    public Page<Announcement> findAnnouncementByAnnouncementTypeAndStatusOrderByCreateDateDesc(AnnouncementType announcementType, Status status, Pageable pageable);

    public Announcement save(Announcement announcement);
}

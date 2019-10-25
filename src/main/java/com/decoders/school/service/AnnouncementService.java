package com.decoders.school.service;

import com.decoders.school.entities.*;
import com.decoders.school.resource.AnnouncementImageResource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnnouncementService {
    public Page<Announcement> findAll(Announcement announcement,Integer page , Integer size);

    public Page<Announcement> findAnnouncement(AnnouncementType announcementType, Status status, Integer page , Integer size);

    public Announcement save(Announcement announcement);

    public Announcement delete(Announcement announcement);

    public void notifyAnnoucment(Announcement announcement);
}

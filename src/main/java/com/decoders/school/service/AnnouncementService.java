package com.decoders.school.service;

import com.decoders.school.entities.*;
import com.decoders.school.resource.AnnouncementImageResource;

import java.util.List;

public interface AnnouncementService {
    public List<Announcement> findAll();

    public List<Announcement> findAnnouncement(AnnouncementType announcementType, Status status);

    public Announcement save(Announcement announcement);
}

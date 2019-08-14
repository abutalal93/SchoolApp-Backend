package com.decoders.school.service;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementType;

import java.util.List;

public interface AnnouncementTypeService {
    public List<AnnouncementType> findAll();
    public AnnouncementType findAnnouncementTypeByCode(String code);
    public AnnouncementType save(AnnouncementType announcement);
}
package com.decoders.school.service.impl;

import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.AnnouncementRepo;
import com.decoders.school.repository.AnnouncementTypeRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.AnnouncementService;
import com.decoders.school.service.AnnouncementTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnnouncementTypeServiceImpl implements AnnouncementTypeService {

    @Autowired
    private AnnouncementTypeRepo announcementTypeRepo;


    @Override
    public List<AnnouncementType> findAll() {
        return announcementTypeRepo.findAll();
    }

    @Override
    public AnnouncementType findAnnouncementTypeByCode(String code) {
        return announcementTypeRepo.findAnnouncementTypeByCode(code);
    }

    @Override
    public AnnouncementType save(AnnouncementType announcement) {
        return announcementTypeRepo.save(announcement);
    }
}

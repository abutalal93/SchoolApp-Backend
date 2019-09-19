package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.*;
import com.decoders.school.repository.AnnouncementRepo;
import com.decoders.school.repository.AnnouncementTypeRepo;
import com.decoders.school.repository.SchoolRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.resource.AnnouncementImageResource;
import com.decoders.school.service.AnnouncementService;
import com.decoders.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private AnnouncementTypeRepo announcementTypeRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private Environment environment;


    @Override
    public Page<Announcement> findAll(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<Announcement> bottomPage = announcementRepo.findAll(pageable);

        return bottomPage;
    }

    @Override
    public Page<Announcement> findAnnouncement(AnnouncementType announcementType, Status status, Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<Announcement> bottomPage = announcementRepo.findAnnouncementByAnnouncementTypeAndStatusOrderByCreateDateDesc(announcementType, status, pageable);

        return bottomPage;
    }

    @Override
    public Announcement save(Announcement announcement) {

        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AnnouncementType announcementType = announcementTypeRepo.findAnnouncementTypeByCode(announcement.getAnnouncementType().getCode());

        announcement.setAnnouncementType(announcementType);
        announcement.setStatus(activeStatus);
        announcement.setCreateDate(LocalDateTime.now());

        return announcementRepo.save(announcement);
    }
}

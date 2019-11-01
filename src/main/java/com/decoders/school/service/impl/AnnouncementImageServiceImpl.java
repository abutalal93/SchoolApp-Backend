package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.Announcement;
import com.decoders.school.entities.AnnouncementImage;
import com.decoders.school.entities.AnnouncementType;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.AnnouncementImageRepo;
import com.decoders.school.repository.AnnouncementRepo;
import com.decoders.school.repository.AnnouncementTypeRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.resource.AnnouncementImageResource;
import com.decoders.school.service.AnnouncementImageService;
import com.decoders.school.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnnouncementImageServiceImpl implements AnnouncementImageService {

    @Autowired
    private AnnouncementImageRepo announcementImageRepo;

    @Override
    public List<AnnouncementImage> findAnnouncementImageByAnnouncement(Announcement announcement) {
        return announcementImageRepo.findAnnouncementImageByAnnouncement(announcement);
    }

    @Override
    public AnnouncementImage save(AnnouncementImage announcementImage) {
        return announcementImageRepo.save(announcementImage);
    }

    @Override
    public void deleteAllByAnnoucment(Announcement announcement) {
        announcementImageRepo.deleteAllByAnnouncement(announcement);
    }
}

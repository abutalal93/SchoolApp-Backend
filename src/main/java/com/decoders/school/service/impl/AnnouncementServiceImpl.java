package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.*;
import com.decoders.school.entities.Class;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.*;
import com.decoders.school.resource.AnnouncementImageResource;
import com.decoders.school.service.AnnouncementService;
import com.decoders.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Autowired
    private AnnouncementImageRepo announcementImageRepo;


    @Override
    public Page<Announcement> findAll(Announcement announcement , Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Announcement> announcementPage = announcementRepo.findAll(new Specification<Announcement>() {

            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if(announcement.getId() != null){
                    predicates.add(cb.equal(root.get("id"), announcement.getId() ));
                }

                if(announcement.getTitle() != null){
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + announcement.getTitle().toLowerCase() + "%"));
                }

                if(announcement.getText() != null){
                    predicates.add(cb.like(cb.lower(root.get("text")), "%" + announcement.getText().toLowerCase() + "%"));
                }

                if(announcement.getAnnouncementType() != null){
                    predicates.add(cb.equal(root.get("announcementType"), announcement.getAnnouncementType() ));
                }

                if(announcement.getExpireDate() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("expireDate"), announcement.getExpireDate() ));
                }

                predicates.add(cb.notEqual(root.get("status"), statusRepo.findStatusByCode("DELETED") ));

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        },pageable);

        return announcementPage;
    }

    @Override
    public Page<Announcement> findAnnouncement(AnnouncementType announcementType, Status status, Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Announcement> bottomPage = announcementRepo.findAnnouncementByAnnouncementTypeAndStatusOrderByCreateDateDesc(announcementType, status, pageable);

        return bottomPage;
    }

    @Override
    public Announcement save(Announcement announcement) {

        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        AnnouncementType announcementType = announcementTypeRepo.findAnnouncementTypeByCode(announcement.getAnnouncementType().getCode());

        announcement.setAnnouncementType(announcementType);
        announcement.setStatus(activeStatus);
        announcement.setCreateDate(Utils.getCurrentDateTimeJordan());

        return announcementRepo.save(announcement);
    }

    @Override
    public Announcement delete(Announcement announcement) {

        Announcement currentAnnouncement = announcementRepo.findAnnouncementById(announcement.getId());
        if (currentAnnouncement == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "announcement_not_found");
        }

        currentAnnouncement.setStatus(statusRepo.findStatusByCode("DELETED"));

        for (AnnouncementImage announcementImage: currentAnnouncement.getAnnouncementImageList()){
            announcementImageRepo.delete(announcementImage);
        }

        return currentAnnouncement;
    }
}

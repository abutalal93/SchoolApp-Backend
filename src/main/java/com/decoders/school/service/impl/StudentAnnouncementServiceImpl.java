package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.*;
import com.decoders.school.entities.Class;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.*;
import com.decoders.school.service.StudentAnnouncementService;
import com.decoders.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentAnnouncementServiceImpl implements StudentAnnouncementService {

    @Autowired
    private StudentAnnouncementRepo studentAnnouncementRepo;

    @Override
    public StudentAnnouncement createStudentAnnoucment(StudentAnnouncement studentAnnouncement) {
        return studentAnnouncementRepo.save(studentAnnouncement);
    }

    @Override
    public Page<StudentAnnouncement> findAll(StudentAnnouncement studentAnnouncementSearchCriteria, Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 1000;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<StudentAnnouncement> studentAnnouncementPage = studentAnnouncementRepo.findAll(new Specification<StudentAnnouncement>() {

            @Override
            public Predicate toPredicate(Root<StudentAnnouncement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if (studentAnnouncementSearchCriteria.getId() != null) {
                    predicates.add(cb.equal(root.get("id"), studentAnnouncementSearchCriteria.getId()));
                }

                if (studentAnnouncementSearchCriteria.getStudent() != null && studentAnnouncementSearchCriteria.getStudent().getId() != null) {
                    predicates.add(cb.equal(root.get("student"), studentAnnouncementSearchCriteria.getStudent()));
                }

                if (studentAnnouncementSearchCriteria.getAnnouncement() != null && studentAnnouncementSearchCriteria.getAnnouncement().getAnnouncementType() != null) {
                    predicates.add(cb.equal(root.get("announcement").<AnnouncementType>get("announcementType"), studentAnnouncementSearchCriteria.getAnnouncement().getAnnouncementType()));
                }

                if(studentAnnouncementSearchCriteria.getAnnouncement() != null && studentAnnouncementSearchCriteria.getAnnouncement().getExpireDate() != null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("announcement").<LocalDateTime>get("expireDate"), studentAnnouncementSearchCriteria.getAnnouncement().getExpireDate()));
                }

                if (studentAnnouncementSearchCriteria.getAnnouncement() != null && studentAnnouncementSearchCriteria.getAnnouncement().getStatus() != null) {
                    predicates.add(cb.equal(root.get("announcement").<Status>get("status"), studentAnnouncementSearchCriteria.getAnnouncement().getStatus()));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        }, pageable);

        return studentAnnouncementPage;
    }

    @Override
    public List<StudentAnnouncement> findAllByAnnoucment(Announcement announcement) {
        return studentAnnouncementRepo.findStudentAnnouncementByAnnouncement(announcement);
    }

    @Override
    public void deleteAllByAnnoucment(Announcement announcement) {
        studentAnnouncementRepo.deleteAllByAnnouncement(announcement);
    }
}

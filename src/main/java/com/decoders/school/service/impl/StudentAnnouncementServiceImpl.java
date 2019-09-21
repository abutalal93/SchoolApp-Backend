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
}

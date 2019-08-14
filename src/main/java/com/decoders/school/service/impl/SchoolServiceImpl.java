package com.decoders.school.service.impl;

import com.decoders.school.entities.School;
import com.decoders.school.repository.SchoolRepo;
import com.decoders.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepo schoolRepo;


    @Override
    public List<School> findAll() {
        return schoolRepo.findAll();
    }

    @Override
    public School findSchoolByUsername(String username) {
        return schoolRepo.findSchoolByUsername(username);
    }
}

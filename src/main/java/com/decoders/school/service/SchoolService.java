package com.decoders.school.service;

import com.decoders.school.entities.School;

import java.util.List;

public interface SchoolService {
    public List<School> findAll();
    public School findSchoolByUsername(String username);
}

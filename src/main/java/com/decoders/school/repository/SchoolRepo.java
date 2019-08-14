package com.decoders.school.repository;

import com.decoders.school.entities.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolRepo extends CrudRepository<School,Long> {
    public List<School> findAll();
    public School findSchoolByUsername(String username);
}

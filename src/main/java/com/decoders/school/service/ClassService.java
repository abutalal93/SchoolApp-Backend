package com.decoders.school.service;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Status;

import java.util.List;

public interface ClassService {
    public List<Class> findAll();

    public Class findClass(String name, AcademicYear academicYear);

    public Class findClassByNameAndAcademicYearAndStatus(String name, AcademicYear academicYear, Status status);

    public Class save(Class clasS);

    public Class update(Class clasS);

    public Class delete(Class clasS);

    public Class findClassById(Long id);
}

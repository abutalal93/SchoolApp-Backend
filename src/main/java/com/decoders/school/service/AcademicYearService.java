package com.decoders.school.service;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AcademicYearService {
    public Page<AcademicYear> findAll(Integer page , Integer size);
    public AcademicYear findAcademicYearById(Long id);
    public AcademicYear findAcademicYearByStatus(Status status);
    public AcademicYear save(AcademicYear academicYear);
    public AcademicYear update(AcademicYear academicYear);
    public AcademicYear delete(AcademicYear academicYear);
    public AcademicYear findAcademicYear(LocalDateTime date);
}

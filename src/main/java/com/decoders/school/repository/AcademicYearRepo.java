package com.decoders.school.repository;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AcademicYearRepo extends CrudRepository<AcademicYear,Long> {
    @Query("select acd from AcademicYear acd where acd.status.id <> 3")
    public Page<AcademicYear> findAll(Pageable pageable);
    public AcademicYear findAcademicYearByStatus(Status status);
    public AcademicYear save(AcademicYear academicYear);
    public AcademicYear findAcademicYearById(Long id);
    @Query("select acd from AcademicYear acd where acd.startDate <= :date and acd.endDate >=:date and acd.status.id <> 3")
    public AcademicYear findAcademicYear(@Param("date")LocalDateTime date);
}

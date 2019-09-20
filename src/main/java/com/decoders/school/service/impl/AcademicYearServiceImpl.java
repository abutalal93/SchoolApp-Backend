package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.AcademicYearRepo;
import com.decoders.school.repository.OtpRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.AcademicYearService;
import com.decoders.school.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {

    @Autowired
    private AcademicYearRepo academicYearRepo;

    @Autowired
    private StatusRepo statusRepo;


    @Override
    public Page<AcademicYear> findAll(Integer page , Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<AcademicYear> academicYearPage = academicYearRepo.findAll(pageable);

        return academicYearPage;
    }

    @Override
    public AcademicYear findAcademicYearById(Long id) {
        return academicYearRepo.findAcademicYearById(id);
    }

    @Override
    public AcademicYear findAcademicYearByStatus(Status status) {
        return academicYearRepo.findAcademicYearByStatus(status);
    }

    @Override
    public AcademicYear save(AcademicYear academicYear) {

//        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYear(academicYear.getStartDate());
//
//        if(currentAcademicYear != null){
//            throw new ResourceException(HttpStatus.FOUND,"academic_year_found");
//        }
//
//        currentAcademicYear = academicYearRepo.findAcademicYear(academicYear.getEndDate());
//
//        if(currentAcademicYear != null){
//            throw new ResourceException(HttpStatus.FOUND,"academic_year_found");
//        }
        academicYear.setAlias(academicYear.getStartDate().getYear() + "/" + academicYear.getEndDate().getYear());
        return academicYearRepo.save(academicYear);
    }

    @Override
    public AcademicYear update(AcademicYear academicYear) {
        AcademicYear updateAcademicYear = academicYearRepo.findAcademicYearById(academicYear.getId());

        if(updateAcademicYear == null){
            throw new ResourceException(HttpStatus.NOT_FOUND,"academic_year_not_found");
        }

//        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYear(academicYear.getStartDate());
//
//        if(currentAcademicYear != null && !currentAcademicYear.getId().equals(updateAcademicYear.getId())){
//            throw new ResourceException(HttpStatus.FOUND,"academic_year_found");
//        }
//
//        currentAcademicYear = academicYearRepo.findAcademicYear(academicYear.getEndDate());
//
//        if(currentAcademicYear != null && !currentAcademicYear.getId().equals(updateAcademicYear.getId())){
//            throw new ResourceException(HttpStatus.FOUND,"academic_year_found");
//        }

        updateAcademicYear.setStartDate(academicYear.getStartDate());
        updateAcademicYear.setEndDate(academicYear.getEndDate());
        updateAcademicYear.setAlias(academicYear.getStartDate().getYear() + "/" + academicYear.getEndDate().getYear());
        return updateAcademicYear;
    }

    @Override
    public AcademicYear delete(AcademicYear academicYear) {
        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(academicYear.getId());

        if(currentAcademicYear == null){
            throw new ResourceException(HttpStatus.NOT_FOUND,"academic_year_not_found");
        }
        currentAcademicYear.setStatus(statusRepo.findStatusByCode("DELETED"));
        return currentAcademicYear;
    }

    @Override
    public AcademicYear findAcademicYear(LocalDateTime date) {
        return academicYearRepo.findAcademicYear(date);
    }
}

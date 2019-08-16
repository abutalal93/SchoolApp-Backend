package com.decoders.school.service.impl;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Status;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.AcademicYearRepo;
import com.decoders.school.repository.ClassRepo;
import com.decoders.school.repository.SectionRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private AcademicYearRepo academicYearRepo;

    @Autowired
    private ClassRepo classRepo;


    @Override
    public List<Section> findAll() {
        return sectionRepo.findAll();
    }

    @Override
    public List<Section> findAll(Section section) {
        List<Section> sectionList = sectionRepo.findAll(new Specification<Section>() {

            @Override
            public Predicate toPredicate(Root<Section> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if(section.getId() != null){
                    predicates.add(cb.equal(root.get("id"), section.getId() ));
                }

                if(section.getName() != null){
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + section.getName().toLowerCase() + "%"));
                }

                if(section.getClasS() != null){
                    predicates.add(cb.equal(root.get("clasS"), section.getClasS() ));
                }

                if(section.getAcademicYear() != null){
                    predicates.add(cb.equal(root.get("academicYear"), section.getAcademicYear() ));
                }

                predicates.add(cb.notEqual(root.get("status"), statusRepo.findStatusByCode("DELETED") ));

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        });

        return sectionList;
    }

    @Override
    public List<Section> findAll(Class clasS) {
        return sectionRepo.findAll(clasS);
    }

    @Override
    public Section findSection(String name, Class clasS, AcademicYear academicYear) {
        return sectionRepo.findSection(name, clasS, academicYear);
    }

    @Override
    public Section findSectionByNameAndClasSAndAcademicYearAndStatus(String name, Class clasS, AcademicYear academicYear, Status status) {
        return sectionRepo.findSectionByNameAndClasSAndAcademicYearAndStatus(name, clasS, academicYear, status);
    }

    @Override
    public Section save(Section section) {

        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(section.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Class currentClass = classRepo.findClassById(section.getClasS().getId());

        if (currentClass == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        Section currentSection = sectionRepo.findSection(section.getName(), currentClass, currentAcademicYear);

        if (currentSection != null) {
            throw new ResourceException(HttpStatus.FOUND, "section_found");
        }

        section.setStatus(statusRepo.findStatusByCode("ACTIVE"));
        section.setAcademicYear(currentAcademicYear);
        return sectionRepo.save(section);
    }

    @Override
    public Section update(Section section) {
        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(section.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Class currentClass = classRepo.findClassById(section.getClasS().getId());

        if (currentClass == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        Section updatedSection = sectionRepo.findSectionById(section.getId());

        if (updatedSection == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "section_not_found");
        }

        Section currentSection = sectionRepo.findSection(section.getName(), currentClass, currentAcademicYear);

        if (currentSection != null && !currentSection.getId().equals(section.getId())) {
            throw new ResourceException(HttpStatus.FOUND, "section_found");
        }

        updatedSection.setName(section.getName());
        updatedSection.setClasS(currentClass);
        return updatedSection;
    }

    @Override
    public Section delete(Section section) {

        Section currentSection = sectionRepo.findSectionById(section.getId());
        if (currentSection == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        currentSection.setStatus(statusRepo.findStatusByCode("DELETED"));
        return currentSection;
    }

    @Override
    public Section findSectionById(Long id) {
        return sectionRepo.findSectionById(id);
    }
}

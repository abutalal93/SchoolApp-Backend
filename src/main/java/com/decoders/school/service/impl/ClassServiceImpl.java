package com.decoders.school.service.impl;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Status;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.AcademicYearRepo;
import com.decoders.school.repository.ClassRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.ClassService;
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
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private AcademicYearRepo academicYearRepo;


    @Override
    public List<Class> findAll() {
        return classRepo.findAll();
    }

    @Override
    public List<Class> findAll(Class clasS) {
        List<Class> classList = classRepo.findAll(new Specification<Class>() {

            @Override
            public Predicate toPredicate(Root<Class> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if(clasS.getId() != null){
                    predicates.add(cb.equal(root.get("id"), clasS.getId() ));
                }

                if(clasS.getName() != null){
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + clasS.getName().toLowerCase() + "%"));
                }

                if(clasS.getAcademicYear() != null){
                    predicates.add(cb.equal(root.get("academicYear"), clasS.getAcademicYear() ));
                }

                predicates.add(cb.notEqual(root.get("status"), statusRepo.findStatusByCode("DELETED") ));

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        });

        return classList;
    }

    @Override
    public Class findClass(String name, AcademicYear academicYear) {
        return classRepo.findClass(name, academicYear);
    }

    @Override
    public Class findClassByNameAndAcademicYearAndStatus(String name, AcademicYear academicYear, Status status) {
        return classRepo.findClassByNameAndAcademicYearAndStatus(name, academicYear, status);
    }

    @Override
    public Class save(Class clasS) {

        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(clasS.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Class currentClass = classRepo.findClass(clasS.getName(), currentAcademicYear);

        if (currentClass != null) {
            throw new ResourceException(HttpStatus.FOUND, "class_found");
        }

        clasS.setStatus(statusRepo.findStatusByCode("ACTIVE"));
        clasS.setAcademicYear(currentAcademicYear);
        return classRepo.save(clasS);
    }

    @Override
    public Class update(Class clasS) {
        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(clasS.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Class updatedClass = classRepo.findClassById(clasS.getId());

        if (updatedClass == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        Class currentClass = classRepo.findClass(clasS.getName(), currentAcademicYear);

        if (currentClass != null && !currentClass.getId().equals(clasS.getId())) {
            throw new ResourceException(HttpStatus.FOUND, "class_found");
        }

        updatedClass.setName(clasS.getName());
        updatedClass.setAcademicYear(currentAcademicYear);
        return updatedClass;
    }

    @Override
    public Class delete(Class clasS) {

        Class currentClass = classRepo.findClassById(clasS.getId());
        if (currentClass == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "class_not_found");
        }

        currentClass.setStatus(statusRepo.findStatusByCode("DELETED"));
        return currentClass;
    }

    @Override
    public Class findClassById(Long id) {
        return classRepo.findClassById(id);
    }
}

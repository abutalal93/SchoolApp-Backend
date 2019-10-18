package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Status;
import com.decoders.school.entities.Student;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.repository.AcademicYearRepo;
import com.decoders.school.repository.SectionRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.repository.StudentRepo;
import com.decoders.school.service.StatusService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AcademicYearRepo academicYearRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private SectionRepo sectionRepo;

    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    public Page<Student> findAll(Student student, Integer page, Integer size) {

        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Student> studentPage = studentRepo.findAll(new Specification<Student>() {

            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if (student.getId() != null) {
                    predicates.add(cb.equal(root.get("id"), student.getId()));
                }

                if (student.getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + student.getName().toLowerCase() + "%"));
                }

                if (student.getSection() != null && student.getSection().getClasS() != null) {
                    predicates.add(cb.equal(root.get("section").<Class>get("clasS"), student.getSection().getClasS()));
                }

                if (student.getSection() != null && student.getSection().getId() != null) {
                    predicates.add(cb.equal(root.get("section"), student.getSection()));
                }

                if (student.getAcademicYear() != null) {
                    predicates.add(cb.equal(root.get("academicYear"), student.getAcademicYear()));
                }

                predicates.add(cb.notEqual(root.get("status"), statusRepo.findStatusByCode("DELETED")));

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        }, pageable);

        return studentPage;
    }

    @Override
    public Student save(Student student) {

        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(student.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Student currentStudent = studentRepo.findStudentByNationalNumberAndAcademicYearAndStatusNot(student.getNationalNumber(), currentAcademicYear, statusRepo.findStatusByCode("DELETED"));

        if (currentStudent != null) {
            throw new ResourceException(HttpStatus.FOUND, "student_found");
        }

        Section section = sectionRepo.findSectionById(student.getSection().getId());

        if (section == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "section_not_found");
        }

        student.setAcademicYear(currentAcademicYear);
        student.setStatus(statusRepo.findStatusByCode("ACTIVE"));
        student.setCreateDate(Utils.getCurrentDateTimeJordan());
        student.setSection(student.getSection());
        return studentRepo.save(student);
    }

    @Override
    public Student update(Student student) {
        AcademicYear currentAcademicYear = academicYearRepo.findAcademicYearById(student.getAcademicYear().getId());

        if (currentAcademicYear == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "academic_year_not_found");
        }

        Student updateStudent = studentRepo.findStudentById(student.getId());

        if (updateStudent == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "student_not_found");
        }

        Section section = sectionRepo.findSectionById(student.getSection().getId());

        if (section == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "section_not_found");
        }

        Student currentStudent = studentRepo.findStudentByNationalNumberAndAcademicYearAndStatusNot(student.getNationalNumber(), currentAcademicYear, statusRepo.findStatusByCode("DELETED"));

        if (currentStudent != null && !currentStudent.getId().equals(student.getId())) {
            throw new ResourceException(HttpStatus.FOUND, "student_found");
        }

        updateStudent.setFatherMobile(student.getFatherMobile());
        updateStudent.setMotherMobile(student.getMotherMobile());
        updateStudent.setGender(student.getGender());
        updateStudent.setName(student.getName());
        updateStudent.setNationalNumber(student.getNationalNumber());
        updateStudent.setSection(student.getSection());
        return updateStudent;
    }

    @Override
    public Student delete(Student student) {
        Student currentStudent = studentRepo.findStudentById(student.getId());

        if (currentStudent == null) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "student_not_found");
        }

        currentStudent.setStatus(statusRepo.findStatusByCode("DELETED"));
        return currentStudent;
    }

    @Override
    public List<Student> findStudent(String mobile, Status status) {
        return studentRepo.findStudent(mobile, status);
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepo.findStudentById(id);
    }

    @Override
    public Student findStudentByNationalNumberAndAcademicYearAndStatusNot(String nationalNumber, AcademicYear academicYear, Status status) {
        return studentRepo.findStudentByNationalNumberAndAcademicYearAndStatusNot(nationalNumber, academicYear, status);
    }

    @Override
    public List<Student> saveAll(List<Student> studentList) {
        return (List<Student>) studentRepo.saveAll(studentList);
    }
}

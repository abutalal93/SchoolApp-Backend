package com.decoders.school.service;

import com.decoders.school.entities.Status;
import com.decoders.school.entities.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    public List<Student> findAll();

    public Page<Student> findAll(Student student, Integer page, Integer size);


    public Student save(Student student);

    public Student update(Student academicYear);

    public Student delete(Student academicYear);

    public List<Student> findStudent(String mobile, Status status);

    public Student findStudentById(Long id);

}

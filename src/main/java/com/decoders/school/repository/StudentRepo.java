package com.decoders.school.repository;

import com.decoders.school.entities.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends CrudRepository<Student, Long> , JpaSpecificationExecutor {
    @Query("select std from Student std where std.status.id <> 3")
    public List<Student> findAll();

    public Student save(Student student);

    public Iterable<Student> saveAll(Iterable studentList);

    public Student findStudentById(Long id);

    public List<Student> findStudentBySection(Section section);

    public Student findStudentByNationalNumberAndAcademicYearAndStatusNot(String nationalNumber , AcademicYear academicYear, Status status);

    @Query("select distinct std from Student std where (std.fatherMobile=:mobile or std.motherMobile=:mobile) and std.status=:status")
    public List<Student> findStudent(@Param("mobile") String mobile, @Param("status") Status status);

    @Query("select stdAnc.student from StudentAnnouncement stdAnc where stdAnc.announcement=:announcement and stdAnc.student.status.id <> 3 and stdAnc.announcement.status <> 3")
    public List<Student> findStudent(@Param("announcement") Announcement announcement);
}

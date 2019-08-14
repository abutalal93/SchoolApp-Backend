package com.decoders.school.repository;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepo extends CrudRepository<Class, Long> {
    @Query("select cls from Class cls where cls.status.id <> 3")
    public List<Class> findAll();

    @Query("select cls from Class cls where cls.status.id <> 3" +
            " and cls.name =:name" +
            " and cls.academicYear=:academicYear")
    public Class findClass(@Param("name") String name, @Param("academicYear") AcademicYear academicYear);

    public Class findClassByNameAndAcademicYearAndStatus(String name, AcademicYear academicYear, Status status);

    public Class save(Class clasS);

    public Class findClassById(Long id);
}

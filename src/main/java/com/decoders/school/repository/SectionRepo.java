package com.decoders.school.repository;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepo extends CrudRepository<Section, Long> {

    @Query("select sec from Section sec where sec.status.id <> 3")
    public List<Section> findAll();

    @Query("select sec from Section sec where sec.status.id <> 3" +
            " and sec.clasS=:clasS")
    public List<Section> findAll(@Param("clasS") Class clasS);

    @Query("select sec from Section sec where sec.status.id <> 3" +
            " and sec.name =:name" +
            " and sec.clasS =:clasS" +
            " and sec.academicYear=:academicYear")
    public Section findSection(@Param("name") String name, @Param("clasS") Class clasS, @Param("academicYear") AcademicYear academicYear);

    public Section findSectionByNameAndClasSAndAcademicYearAndStatus(String name , Class clasS , AcademicYear academicYear, Status status);

    public Section save(Section clasS);

    public Section findSectionById(Long id);
}

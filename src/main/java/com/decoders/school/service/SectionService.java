package com.decoders.school.service;

import com.decoders.school.entities.AcademicYear;
import com.decoders.school.entities.Class;
import com.decoders.school.entities.Section;
import com.decoders.school.entities.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SectionService {
    public List<Section> findAll();

    public Page<Section> findAll(Section section, Integer page , Integer size);

    public List<Section> findAll(Class clasS);

    public Section findSection(String name, Class clasS, AcademicYear academicYear);

    public Section findSectionByNameAndClasSAndAcademicYearAndStatus(String name, Class clasS, AcademicYear academicYear, Status status);

    public Section save(Section section);

    public Section update(Section section);

    public Section delete(Section section);

    public Section findSectionById(Long id);
}

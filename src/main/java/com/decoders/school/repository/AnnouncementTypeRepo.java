package com.decoders.school.repository;

import com.decoders.school.entities.AnnouncementType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementTypeRepo extends CrudRepository<AnnouncementType,Long> {
    public List<AnnouncementType> findAll();
    public AnnouncementType findAnnouncementTypeByCode(String code);
    public AnnouncementType save(AnnouncementType announcement);
}

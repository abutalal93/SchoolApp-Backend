package com.decoders.school.repository;

import com.decoders.school.entities.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentAnnouncementRepo extends CrudRepository<StudentAnnouncement, Long> , JpaSpecificationExecutor {
    public StudentAnnouncement save(StudentAnnouncement studentAnnouncement);
}

package com.decoders.school.service;

import com.decoders.school.entities.Status;
import com.decoders.school.entities.Student;
import com.decoders.school.entities.StudentAnnouncement;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentAnnouncementService {
    public StudentAnnouncement createStudentAnnoucment(StudentAnnouncement studentAnnouncement);
}

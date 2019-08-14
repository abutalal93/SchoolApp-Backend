package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STUDENT_ANNOUNCMENT")
public class StudentAnnouncement implements Serializable {

    @Id
    @SequenceGenerator(name = "StudentAnnouncementSeq", sequenceName = "STUDENT_ANNOUNCMENT_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "StudentAnnouncementSeq", strategy = GenerationType.AUTO)
    @Column(name = "std_annc_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "std_annc_std_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "std_annc_annc_id", nullable = false)
    private Announcement announcement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}

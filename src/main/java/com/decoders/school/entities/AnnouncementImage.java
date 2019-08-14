package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANNOUNCMENT_IMAGE")
public class AnnouncementImage implements Serializable {

    @Id
    @SequenceGenerator(name = "AnnouncementImageSeq", sequenceName = "ANNOUNCMENT_IMAGE_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "AnnouncementImageSeq", strategy = GenerationType.AUTO)
    @Column(name = "annc_img_id")
    private Long id;

    @Column(name = "annc_img_url" , nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "annc_id", nullable = false)
    private Announcement announcement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}

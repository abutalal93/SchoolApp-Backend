package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ANNOUNCMENT")
public class Announcement implements Serializable {

    @Id
    @SequenceGenerator(name = "AnnouncementSeq", sequenceName = "ANNOUNCMENT_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "AnnouncementSeq", strategy = GenerationType.AUTO)
    @Column(name = "annc_id")
    private Long id;

    @Column(name = "annc_title")
    private String title;

    @Column(name = "annc_text", columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "annc_create_date")
    private LocalDateTime createDate;

    @Column(name = "annc_expire_date")
    private LocalDateTime expireDate;

    @OneToOne
    @JoinColumn(name = "annc_sts_id", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "annc_typ_id", nullable = false)
    private AnnouncementType announcementType;

    @OneToMany(mappedBy="announcement" , fetch = FetchType.LAZY)
    private List<AnnouncementImage> announcementImageList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AnnouncementType getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(AnnouncementType announcementType) {
        this.announcementType = announcementType;
    }

    public List<AnnouncementImage> getAnnouncementImageList() {
        return announcementImageList;
    }

    public void setAnnouncementImageList(List<AnnouncementImage> announcementImageList) {
        this.announcementImageList = announcementImageList;
    }
}

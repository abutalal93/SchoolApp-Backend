package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ANNOUNCMENT_TYPE")
public class AnnouncementType implements Serializable {

    @Id
    @SequenceGenerator(name = "StatusSeq", sequenceName = "Status_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "StatusSeq", strategy = GenerationType.AUTO)
    @Column(name = "annc_typ_id" , updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "annc_typ_code", nullable = false, unique = true)
    private String code;

    @Column(name = "annc_typ_capti_ar")
    private String captionAr;

    @Column(name = "annc_typ_capti_en")
    private String captionEn;

    @OneToOne
    @JoinColumn(name = "annc_type_sts_id", nullable = false)
    private Status status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCaptionAr() {
        return captionAr;
    }

    public void setCaptionAr(String captionAr) {
        this.captionAr = captionAr;
    }

    public String getCaptionEn() {
        return captionEn;
    }

    public void setCaptionEn(String captionEn) {
        this.captionEn = captionEn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

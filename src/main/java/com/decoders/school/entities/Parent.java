package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARENT")
public class Parent implements Serializable {

    @Id
    @SequenceGenerator(name = "ParentSeq", sequenceName = "PARENT_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "ParentSeq", strategy = GenerationType.AUTO)
    @Column(name = "prt_id")
    private Long id;

    @Column(name = "prt_full_name")
    private String name;

    @Column(name = "prt_national_number")
    private String nationalNumber;

    @Column(name = "prt_create_date")
    private LocalDateTime createDate;

    @Column(name = "prt_mobile")
    private String mobile;

    @Column(name = "prt_dvc_platform")
    private String platform;

    @Column(name = "prt_dvc_token")
    private String token;
    
    @OneToOne
    @JoinColumn(name = "prt_sts_id", nullable = false)
    private Status status;

//    @OneToOne
//    @JoinColumn(name = "prt_mob_dev_id", nullable = false)
//    private MobileDevice mobileDevice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public MobileDevice getMobileDevice() {
//        return mobileDevice;
//    }
//
//    public void setMobileDevice(MobileDevice mobileDevice) {
//        this.mobileDevice = mobileDevice;
//    }
}

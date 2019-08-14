package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "OTP")
public class Otp implements Serializable {

    @Id
    @SequenceGenerator(name = "OtpSeq", sequenceName = "OTP_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "OtpSeq", strategy = GenerationType.AUTO)
    @Column(name = "otp_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "otp_code", nullable = false , unique = true)
    private String code;

    @Column(name = "otp_create_date")
    private LocalDateTime createDate;

    @Column(name = "otp_expire_date")
    private LocalDateTime expireDate;

    @Column(name = "otp_mobile", nullable = false)
    private String mobile;

    @OneToOne
    @JoinColumn(name = "otp_sts_id", nullable = false)
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

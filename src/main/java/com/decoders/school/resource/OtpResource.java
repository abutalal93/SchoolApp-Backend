package com.decoders.school.resource;

import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OtpResource {
    
    private Long id;
    
    private String code;
    
    private LocalDateTime createDate;

    private LocalDateTime expireDate;

    private String mobile;

    private Long statusId;

    private String statusCode;

    private String countryCode;

    private String token;

    private String platform;

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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public static OtpResource toResource(Otp otp)
    {
        OtpResource otpResource = new OtpResource();

        otpResource.setId(otp.getId());
        otpResource.setCreateDate(otp.getCreateDate());
        otpResource.setExpireDate(otp.getExpireDate());
        otpResource.setMobile(otp.getMobile());
        otpResource.setCode(otp.getCode());
        otpResource.setMobile(otp.getMobile());

        if(otp.getStatus() != null) {
            otpResource.setStatusId(otp.getStatus().getId());
            otpResource.setStatusCode(otp.getStatus().getCode());
        }

        return otpResource;
    }

    public static List<OtpResource> toResource(List<Otp> otpList){
        List<OtpResource> otpResourceList = new ArrayList<>();
        otpList.forEach(otp -> {
            OtpResource otpResource = toResource(otp);
            otpResourceList.add(otpResource);
        });
        return otpResourceList;
    }

    public Otp toOtp(){
        Otp otp = new Otp();

        otp.setId(this.id);
        otp.setCreateDate(this.createDate);
        otp.setExpireDate(this.expireDate);
        otp.setCode(this.code);
        otp.setMobile(this.mobile);

        if(statusId == null){
            Status status = new Status();
            status.setId(this.id);
            otp.setStatus(status);
        }
        return otp;
    }

}

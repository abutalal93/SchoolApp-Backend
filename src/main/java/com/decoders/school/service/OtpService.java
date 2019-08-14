package com.decoders.school.service;

import com.decoders.school.entities.Otp;
import com.decoders.school.entities.School;
import com.decoders.school.entities.Status;

import java.util.List;

public interface OtpService {
    public List<Otp> findAll();
    public Otp findOtpByMobileAndStatus(String mobile , Status status);
    public Otp create(Otp otp);
    public Otp updateOtp(String code , Status status);
}

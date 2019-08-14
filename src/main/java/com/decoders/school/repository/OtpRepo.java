package com.decoders.school.repository;

import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OtpRepo extends CrudRepository<Otp,Long> {
    public List<Otp> findAll();
    public Otp findOtpByMobileAndStatus(String mobile , Status status);
    public Otp save(Otp otp);
}

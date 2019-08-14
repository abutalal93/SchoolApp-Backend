package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.OtpRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private StatusRepo statusRepo;


    @Override
    public List<Otp> findAll() {
        return otpRepo.findAll();
    }

    @Override
    public Otp findOtpByMobileAndStatus(String mobile, Status status) {
        return otpRepo.findOtpByMobileAndStatus(mobile,status);
    }

    @Override
    public Otp create(Otp otp) {
        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        Otp currentOtp = otpRepo.findOtpByMobileAndStatus(otp.getMobile(),activeStatus);

        if(currentOtp != null){
            currentOtp.setStatus(statusRepo.findStatusByCode("INACTIVE"));
            otp.setStatus(activeStatus);
            otp.setCode(Utils.randomNumber(4));
            otp.setCreateDate(LocalDateTime.now());
            otp.setExpireDate(LocalDateTime.now().plusMinutes(1));
            return otpRepo.save(otp);
        }else{
            otp.setStatus(activeStatus);
            otp.setCode(Utils.randomNumber(4));
            otp.setCreateDate(LocalDateTime.now());
            otp.setExpireDate(LocalDateTime.now().plusMinutes(1));
            return otpRepo.save(otp);
        }
    }

    @Override
    public Otp updateOtp(String mobile, Status status) {
        Otp otp = otpRepo.findOtpByMobileAndStatus(mobile,statusRepo.findStatusByCode("ACTIVE"));
        otp.setStatus(status);
        return otp;
    }
}

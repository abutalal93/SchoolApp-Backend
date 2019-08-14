package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.OtpRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.OtpService;
import com.decoders.school.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepo statusRepo;


    @Override
    public Status findStatusByCode(String code) {
        return statusRepo.findStatusByCode(code);
    }
}

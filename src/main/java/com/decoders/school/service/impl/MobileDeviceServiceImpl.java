package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.MobileDevice;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.MobileDeviceRepo;
import com.decoders.school.repository.ParentRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.MobileDeviceService;
import com.decoders.school.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MobileDeviceServiceImpl implements MobileDeviceService {

    @Autowired
    private MobileDeviceRepo mobileDeviceRepo;

    @Override
    public List<MobileDevice> findAll() {
        return mobileDeviceRepo.findAll();
    }

    @Override
    public MobileDevice save(MobileDevice mobileDevice) {
        return mobileDeviceRepo.save(mobileDevice);
    }

    @Override
    public MobileDevice findMobileDeviceById(Long id) {
        return mobileDeviceRepo.findMobileDeviceById(id);
    }

    @Override
    public MobileDevice findMobileDeviceByToken(String token) {
        return mobileDeviceRepo.findMobileDeviceByToken(token);
    }
}

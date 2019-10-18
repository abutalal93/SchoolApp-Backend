package com.decoders.school.service;

import com.decoders.school.entities.MobileDevice;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;

import java.util.List;

public interface MobileDeviceService {
    public List<MobileDevice> findAll();

    public MobileDevice save(MobileDevice mobileDevice);

    public MobileDevice findMobileDeviceById(Long id);

    public MobileDevice findMobileDeviceByToken(String token);
}

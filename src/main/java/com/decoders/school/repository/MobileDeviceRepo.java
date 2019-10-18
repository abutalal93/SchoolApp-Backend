package com.decoders.school.repository;

import com.decoders.school.entities.MobileDevice;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MobileDeviceRepo extends CrudRepository<MobileDevice, Long> {
    public List<MobileDevice> findAll();

    public MobileDevice save(MobileDevice mobileDevice);

    public MobileDevice findMobileDeviceById(Long id);

    public MobileDevice findMobileDeviceByToken(String token);
}

package com.decoders.school.service;

import com.decoders.school.entities.Otp;
import com.decoders.school.entities.Status;

import java.util.List;

public interface StatusService {
    public Status findStatusByCode(String code);
}

package com.decoders.school.service.impl;

import com.decoders.school.Utils.Utils;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;
import com.decoders.school.repository.ParentRepo;
import com.decoders.school.repository.StatusRepo;
import com.decoders.school.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private StatusRepo statusRepo;


    @Override
    public List<Parent> findAll() {
        return parentRepo.findAll();
    }

    @Override
    public Parent createOrUpdate(String mobile, String platform, String token) {

        Status activeStatus = statusRepo.findStatusByCode("ACTIVE");

        Parent parent = parentRepo.findParent(mobile, activeStatus);

        if (parent != null) {
            parent.setMobile(mobile);
            parent.setToken(token);
            parent.setPlatform(platform);
            parent.setStatus(activeStatus);

            return parent;
        } else {
            parent = new Parent();
            parent.setMobile(mobile);
            parent.setToken(token);
            parent.setPlatform(platform);
            parent.setStatus(activeStatus);
            parent.setCreateDate(Utils.getCurrentDateTimeJordan());

            return parentRepo.save(parent);
        }
    }

    @Override
    public Parent findParent(String mobile, Status status) {
        return parentRepo.findParent(mobile, status);
    }
}

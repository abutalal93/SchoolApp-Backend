package com.decoders.school.service;

import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;

import java.util.List;

public interface ParentService {
    public List<Parent> findAll();

    public Parent createOrUpdate(String mobile , String platform , String token);

    public Parent findParent(String mobile, Status status);
}

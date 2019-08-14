package com.decoders.school.repository;

import com.decoders.school.entities.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepo extends CrudRepository<Status,Long> {
    public Status findStatusByCode(String code);
}

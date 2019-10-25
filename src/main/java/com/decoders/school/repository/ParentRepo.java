package com.decoders.school.repository;

import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Status;
import com.decoders.school.entities.Parent;
import com.decoders.school.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParentRepo extends CrudRepository<Parent, Long> {
    public List<Parent> findAll();

    public Parent save(Parent parent);

    @Query("select prt from Parent prt where prt.mobile=:mobile and prt.status=:status")
    public Parent findParent(@Param("mobile") String mobile, @Param("status") Status status);

    @Query("select prt from Parent prt where (prt.mobile=:fatherMobile or prt.mobile=:motherMobile) and (prt.status=:status)")
    public List<Parent> findParent(@Param("fatherMobile") String fatherMobile, @Param("motherMobile") String motherMobile, @Param("status") Status status);
}

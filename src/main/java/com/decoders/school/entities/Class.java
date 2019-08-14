package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Class implements Serializable {

    @Id
    @SequenceGenerator(name = "ClassSeq", sequenceName = "Class_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "ClassSeq", strategy = GenerationType.AUTO)
    @Column(name = "cls_id" , updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "cls_name" , nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "cls_sts_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "cls_acc_yr_id")
    private AcademicYear academicYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }
}

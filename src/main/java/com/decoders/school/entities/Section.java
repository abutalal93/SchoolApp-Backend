package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Section", uniqueConstraints = {@UniqueConstraint(columnNames = {"sec_name", "sec_cls_id", "sec_acc_yr_id"})})
public class Section implements Serializable {

    @Id
    @SequenceGenerator(name = "SectionSeq", sequenceName = "Section_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SectionSeq", strategy = GenerationType.AUTO)
    @Column(name = "sec_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "sec_name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "sec_cls_id", nullable = false)
    private Class clasS;

    @OneToOne
    @JoinColumn(name = "sec_sts_id", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "sec_acc_yr_id", nullable = false)
    private AcademicYear academicYear;

    public Section(){

    }

    public Section(Class clasS) {
        this.clasS = clasS;
    }

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

    public Class getClasS() {
        return clasS;
    }

    public void setClasS(Class clasS) {
        this.clasS = clasS;
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

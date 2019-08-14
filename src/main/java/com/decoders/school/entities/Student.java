package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Student implements Serializable {

    @Id
    @SequenceGenerator(name = "StudentSeq", sequenceName = "STUDENT_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "StudentSeq", strategy = GenerationType.AUTO)
    @Column(name = "std_id")
    private Long id;

    @Column(name = "std_full_name")
    private String name;

    @Column(name = "std_gender")
    private String gender;

    @Column(name = "std_national_number")
    private String nationalNumber;

    @Column(name = "std_create_date")
    private LocalDateTime createDate;

    @Column(name = "std_image")
    private String image;

    @Column(name = "std_father_mobile")
    private String fatherMobile;

    @Column(name = "std_mother_mobile")
    private String motherMobile;

    @OneToOne
    @JoinColumn(name = "std_sts_id", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "sec_sts_id", nullable = false)
    private Section section;

    @OneToOne
    @JoinColumn(name = "sec_acc_yr_id", nullable = false)
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFatherMobile() {
        return fatherMobile;
    }

    public void setFatherMobile(String fatherMobile) {
        this.fatherMobile = fatherMobile;
    }

    public String getMotherMobile() {
        return motherMobile;
    }

    public void setMotherMobile(String motherMobile) {
        this.motherMobile = motherMobile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }
}

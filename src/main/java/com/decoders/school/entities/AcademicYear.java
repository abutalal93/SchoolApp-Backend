package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACADEMIC_YEAR")
public class AcademicYear implements Serializable {

    @Id
    @SequenceGenerator(name = "AcademicYearSeq", sequenceName = "ACADEMIC_YEAR_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "AcademicYearSeq", strategy = GenerationType.AUTO)
    @Column(name = "acc_yr_id")
    private Long id;

    @Column(name = "acc_yr_alias")
    private String alias;

    @Column(name = "acc_yr_start_date")
    private LocalDateTime startDate;

    @Column(name = "acc_yr_end_date")
    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn(name = "acc_yr_sts_id", nullable = false)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

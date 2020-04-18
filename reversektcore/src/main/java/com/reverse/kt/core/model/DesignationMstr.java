package com.reverse.kt.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="DESIGNATION_MSTR")
@Getter
@Setter
@NoArgsConstructor
public class DesignationMstr extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DESIGNATION_MSTR_SEQ")
    private Integer designationMstrSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @Column(name = "DESIGNATION_NAME",length = 100)
    private String designatioName;

    @Column(name = "PRIORITY",length = 4)
    private Integer priority;
}

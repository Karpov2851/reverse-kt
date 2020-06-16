package com.reverse.kt.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="COMPANY_MSTR")
@Getter
@Setter
@NoArgsConstructor
public class CompanyMstr extends BaseEntity{

    @Builder
    public CompanyMstr(Integer companyMstrSeq, String companyName, String companyCd, Integer createdBy,
                       Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.companyMstrSeq = companyMstrSeq;
        this.companyName = companyName;
        this.companyCd = companyCd;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMPANY_MSTR_SEQ")
    private Integer companyMstrSeq;

    @Column(name = "COMPANY_NAME",length = 50)
    private String companyName;

    @Column(name = "COMPANY_CD", length = 50)
    private String companyCd;

    @OneToMany(mappedBy = "companyMstr",fetch = FetchType.EAGER)
    private Set<BusinessUnit> businessUnits = new HashSet<>();

    @OneToMany(mappedBy = "companyMstr",fetch = FetchType.EAGER)
    private Set<SkillMstr> skillMstrs = new HashSet<>();

    @OneToMany(mappedBy = "companyMstr",fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "companyMstr",fetch = FetchType.EAGER)
    private Set<DesignationMstr> designationMstrs = new HashSet<>();

}

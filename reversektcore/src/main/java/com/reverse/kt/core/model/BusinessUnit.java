package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="BUSINESS_UNIT")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BusinessUnit extends BaseEntity{

    @Builder
    public BusinessUnit(CompanyMstr companyMstr,String businessUnitName,String businessCd,String businessDesc,
                        Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.companyMstr = companyMstr;
        this.businessUnitName = businessUnitName;
        this.businessCd = businessCd;
        this.businessCd = businessCd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BUSINESS_UNIT_SEQ")
    private Integer businessUnitSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @Column(name = "BUSINESS_UNIT_NAME",length = 50)
    private String businessUnitName;

    @Column(name = "BUSINESS_CD",length = 50)
    private String businessCd;

    @Column(name = "BUSINESS_DESC")
    private String businessDesc;

}

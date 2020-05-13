package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="DESIGNATION_MSTR")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DesignationMstr extends BaseEntity{

    @Builder
    public DesignationMstr(Integer designationMstrSeq,CompanyMstr companyMstr,String designatioName,Integer priority,
                        Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.designationMstrSeq = designationMstrSeq;
        this.companyMstr = companyMstr;
        this.designatioName = designatioName;
        this.priority = priority;
    }

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

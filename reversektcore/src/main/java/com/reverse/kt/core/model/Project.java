package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="PROJECT")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Project extends BaseEntity implements java.io.Serializable{


    @Builder
    public Project(Integer projectSeq, CompanyMstr companyMstr, BusinessUnit businessUnit, String projectName, String projectDesc,
                   Integer createdBy, Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.projectSeq = projectSeq;
        this.companyMstr = companyMstr;
        this.businessUnit = businessUnit;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PROJECT_SEQ")
    private Integer projectSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @OneToOne
    @JoinColumn(name = "BUSINESS_UNIT_SEQ")
    private BusinessUnit businessUnit;

    @OneToMany(mappedBy = "project",fetch = FetchType.EAGER)
    private Set<ProjectItem> projectItems = new HashSet<>();

    @Column(name = "PROJECT_NAME",length = 50)
    private String projectName;

    @Column(name = "PROJECT_DESC",length = 50)
    private String projectDesc;

}

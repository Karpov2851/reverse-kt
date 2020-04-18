package com.reverse.kt.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "project")
    private Set<ProjectItem> projectItems;

    @Column(name = "PROJECT_NAME",length = 50)
    private String projectName;

    @Column(name = "PROJECT_DESC",length = 50)
    private String projectDesc;

}

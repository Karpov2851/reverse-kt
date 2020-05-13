package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="SKILL_MSTR")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SkillMstr extends BaseEntity{

    @Builder
    public SkillMstr(Integer skillMstrSeq,CompanyMstr companyMstr,String skillDesc,String skillDomain,
                       Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.skillMstrSeq = skillMstrSeq;
        this.companyMstr = companyMstr;
        this.skillDesc = skillDesc;
        this.skillDomain = skillDomain;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SKILL_MSTR_SEQ")
    private Integer skillMstrSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @OneToMany(
            cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "skillMstr"
    )
    private Set<ProjectItemSkill> projectItemSkills = new HashSet<>();

    @Column(name = "SKILL_DESC",length = 50)
    private String skillDesc;

    @Column(name = "SKILL_DOMAIN",length = 50)
    private String skillDomain;



}

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
@Table(name="SKILL_MSTR")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SkillMstr extends BaseEntity{

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
    private Set<ProjectItemSkill> projectItemSkills;

    @Column(name = "SKILL_DESC",length = 50)
    private String skillDesc;

    @Column(name = "SKILL_DOMAIN",length = 50)
    private String skillDomain;



}

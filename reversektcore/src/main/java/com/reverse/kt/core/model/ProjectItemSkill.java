package com.reverse.kt.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="PROJECT_ITEM_SKILL")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectItemSkill extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PROJECT_ITEM_SKILL_MSTR_SEQ")
    private Integer projectItemSkillMstrSeq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PROJECT_ITEM_SEQ")
    @EqualsAndHashCode.Exclude private ProjectItem projectItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SKILL_MSTR_SEQ")
    @EqualsAndHashCode.Exclude private SkillMstr skillMstr;
}

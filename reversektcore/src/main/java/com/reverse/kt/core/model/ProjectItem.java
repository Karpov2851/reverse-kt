package com.reverse.kt.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="PROJECT_ITEM")
@Getter
@Setter
@NoArgsConstructor
public class ProjectItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PROJECT_ITEM_SEQ")
    private Integer projectItemSeq;

    @ManyToOne
    @JoinColumn(name = "PROJECT_SEQ")
    private Project project;

    @OneToMany(
            cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "projectItem"
    )
    private Set<ProjectItemSkill> projectItemSkills;

    @Column(name = "PROJECT_ITEM_DESC",length = 50)
    private String projectItemDesc;
}

package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="PROJECT_ITEM")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectItem extends BaseEntity{

    @Builder
    public ProjectItem(Integer projectItemSeq, String projectItemDesc,
                   Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.projectItemSeq = projectItemSeq;
        this.projectItemDesc = projectItemDesc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PROJECT_ITEM_SEQ")
    private Integer projectItemSeq;

    @ManyToOne
    @JoinColumn(name = "PROJECT_SEQ")
    @EqualsAndHashCode.Exclude private Project project;

    @OneToMany(
            cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "projectItem"
    )
    @EqualsAndHashCode.Exclude private Set<ProjectItemSkill> projectItemSkills;

    @Column(name = "PROJECT_ITEM_DESC",length = 50)
    private String projectItemDesc;
}

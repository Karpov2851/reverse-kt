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
@Table(name="FEEDBACK_QUESTION")
@Getter
@Setter
@NoArgsConstructor
public class FeedbackQuestion extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FEEDBACK_QUESTION_SEQ")
    private Integer feedbackQuestionSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ITEM_SEQ")
    private ProjectItem projectItem;

    @OneToMany(mappedBy = "feedbackQuestion")
    private Set<FeedbackAnswer> feedbackAnswers;

    @Column(name = "FEEDBACK_QUESTION_DESC")
    private String feedbackQuestionDesc;

    @Column(name = "PRIORITY",length = 4)
    private Integer priority;


}

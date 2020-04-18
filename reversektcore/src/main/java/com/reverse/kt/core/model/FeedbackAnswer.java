package com.reverse.kt.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="FEEDBACK_ANSWER")
@Getter
@Setter
@NoArgsConstructor
public class FeedbackAnswer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FEEDBACK_ANSWER_SEQ")
    private Integer feedbackAnswerSeq;

    @ManyToOne
    @JoinColumn(name = "FEEDBACK_QUESTION_SEQ")
    private FeedbackQuestion feedbackQuestion;

    @Column(name = "FEEDBACK_ANSWER_DESC",length = 200)
    private String feedbackAnswerDesc;
}

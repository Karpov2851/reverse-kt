package com.reverse.kt.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikas on 07-04-2020.
 */
@Entity
@Table(name="SCHEDULED_SESSION")
@Getter
@Setter
@NoArgsConstructor
public class ScheduledSession extends BaseEntity{

    @Builder
    public ScheduledSession(Integer scheduledSessionSeq,ProjectItem projectItem, VideoDetails videoDetails, LocalDateTime sessionStartDateTime,
                            LocalDateTime sessionEndDateTime,
                            Integer createdBy, Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.scheduledSessionSeq = scheduledSessionSeq;
        this.projectItem = projectItem;
        this.videoDetails = videoDetails;
        this.sessionStartDateTime = sessionStartDateTime;
        this.sessionEndDateTime = sessionEndDateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SCHEDULED_SESSION_SEQ")
    private Integer scheduledSessionSeq;

    @OneToOne
    @JoinColumn(name = "PROJECT_ITEM_SEQ")
    @EqualsAndHashCode.Exclude private ProjectItem projectItem;

    @OneToOne
    @JoinColumn(name = "VIDEO_DETAIL_SEQ")
    @EqualsAndHashCode.Exclude private VideoDetails videoDetails;

    @Column(name="SESSION_START_DATE_TIME")
    @Basic
    private LocalDateTime sessionStartDateTime;

    @Column(name="SESSION_END_DATE_TIME")
    @Basic
    private LocalDateTime sessionEndDateTime;

    @OneToMany(
            cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "scheduledSession"
    )
    @EqualsAndHashCode.Exclude private Set<ScheduledSessionUser> scheduledSessionUsers = new HashSet<>();
}

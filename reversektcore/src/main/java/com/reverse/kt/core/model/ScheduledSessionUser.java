package com.reverse.kt.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by vikas on 03-06-2020.
 */
@Entity
@Table(name="SCHEDULED_SESSION_USER")
@Getter
@Setter
@NoArgsConstructor
public class ScheduledSessionUser extends BaseEntity{

    @Builder
    public ScheduledSessionUser(ScheduledSession scheduledSession, UserProfile userProfile,
                                Integer createdBy, Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.scheduledSession = scheduledSession;
        this.userProfile = userProfile;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SCHEDULED_SESSION_USER_SEQ")
    private Integer scheduledSessionUserSeq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SCHEDULED_SESSION")
    private ScheduledSession scheduledSession;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_PROFILE")
    private UserProfile userProfile;

}

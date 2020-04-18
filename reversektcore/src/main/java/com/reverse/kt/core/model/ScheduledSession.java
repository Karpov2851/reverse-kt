package com.reverse.kt.core.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vikas on 07-04-2020.
 */
@Entity
@Table(name="SCHEDULED_SESSION")
public class ScheduledSession extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SCHEDULED_SESSION_SEQ")
    private Integer scheduledSessionSeq;

    @OneToOne
    @JoinColumn(name = "VIDEO_DETAIL_SEQ")
    private VideoDetails videoDetails;

    @Column(name="SCHEDULED_SESSION_DATE")
    @Temporal(TemporalType.DATE)
    private Date scheduledSessionDate;
}

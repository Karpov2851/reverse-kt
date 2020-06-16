package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.ScheduledSessionDao;
import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.ui.HistoryView;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by vikas on 11-06-2020.
 */
@Service
@Transactional
@Setter(onMethod = @__(@Inject))
public class ScheduleSessionService {

    private ScheduledSessionDao scheduledSessionDao;

    public List<HistoryView> fetchHistoryViewForUser(int userProfileSeq) throws Exception{
        List<ScheduledSession> scheduledSessionsForUser = scheduledSessionDao.fetchScheduledSessionForUser(userProfileSeq);
        return Optional.ofNullable(scheduledSessionsForUser).orElseGet(Collections::emptyList).stream()
                .map(t->HistoryView.builder().scheduledSessionSeq(t.getScheduledSessionSeq())
                        .videoName(t.getVideoDetails().getVideoName()).videoDetails(t.getVideoDetails().getVideoDetails()).build()).collect(Collectors.toList());
    }
}

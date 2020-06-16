package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.ScheduledSessionDao;
import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.model.VideoDetails;
import com.reverse.kt.core.ui.HistoryView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 11-06-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleSessionServiceTest {

    @Mock
    private ScheduledSessionDao scheduledSessionDao;

    @InjectMocks
    private ScheduleSessionService scheduleSessionService;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Before
    public void setUpResources(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchHistoryViewForUserTest() throws Exception{
        //given
        when(scheduledSessionDao.fetchScheduledSessionForUser(integerArgumentCaptor.capture())).
                thenReturn(scheduledSessionDaoDataSet());

        //when
        List<HistoryView> expected = scheduleSessionService.fetchHistoryViewForUser(1);

        //then
        assertThat(expected,hasSize(2));
        assertThat(expected.stream().filter(t->t.getScheduledSessionSeq() == 1).findFirst().get().getVideoName()
                ,equalTo("test video"));
        assertThat(integerArgumentCaptor.getValue(),equalTo(1));
    }

    private List<ScheduledSession> scheduledSessionDaoDataSet(){
       return Arrays.asList(ScheduledSession.builder().videoDetails(VideoDetails.builder().videoDetails("video details").videoName("test video").build()).scheduledSessionSeq(1).build(),
                ScheduledSession.builder().videoDetails(VideoDetails.builder().videoDetails("video details one").videoName("test video one").build()).scheduledSessionSeq(2).build());
    }

    private List<HistoryView> expectedDataSet(){
        return Arrays.asList(HistoryView.builder().scheduledSessionSeq(1).videoDetails("video details").videoName("test video").build(),
                HistoryView.builder().scheduledSessionSeq(2).videoDetails("video details one").videoName("test video one").build());
    }
}

package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.ProjectItem;
import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.model.VideoDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 05-06-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts = {"classpath:db/scheduled_session_data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ScheduledSessionDaoTest extends BaseTestDao<ScheduledSession,ScheduledSessionDao,Integer> {

    @Inject
    private ScheduledSessionDao scheduledSessionDao;

    @Test
    @Sql(scripts = {"classpath:db/scheduled_session_dao_fetch_scheduled_session_user_data.sql"})
    public void fetchScheduledSessionForUserTest() throws Exception{
        //when
        List<ScheduledSession> actual = scheduledSessionDao.fetchScheduledSessionForUser(1);

        //then
        assertNotNull(actual);
        assertThat(actual,hasSize(2));
    }

    @Override
    protected ScheduledSession getSingleEntity() {
        return ScheduledSession.builder().projectItem(ProjectItem.builder().projectItemSeq(1).projectItemDesc("test desc").build()).
                videoDetails(VideoDetails.builder().videoDetailSeq(1).videoName("video").build()).sessionStartDateTime(LocalDateTime.now())
        .sessionEndDateTime(LocalDateTime.now().plus(1, ChronoUnit.HOURS)).build();
    }

    @Override
    protected ScheduledSessionDao getDao() {
        return scheduledSessionDao;
    }

    @Override
    public void verifySave() {
        assertThat(findById(1).getVideoDetails().getVideoName(),equalTo(getSingleEntity().getVideoDetails().getVideoName()));
    }
}

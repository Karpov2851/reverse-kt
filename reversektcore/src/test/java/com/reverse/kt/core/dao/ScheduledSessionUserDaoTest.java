package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.model.ScheduledSessionUser;
import com.reverse.kt.core.model.UserProfile;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 05-06-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts = {"classpath:db/scheduled_session_user_data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ScheduledSessionUserDaoTest extends BaseTestDao<ScheduledSessionUser,ScheduledSessionUserDao,Integer>{

    @Inject
    private ScheduledSessionUserDao scheduledSessionUserDao;

    @Override
    protected ScheduledSessionUser getSingleEntity() {
        return ScheduledSessionUser.builder().userProfile(UserProfile.builder().userProfileSeq(1).userId("user_id").build())
                .scheduledSession(ScheduledSession.builder().scheduledSessionSeq(1).sessionEndDateTime(LocalDateTime.now()).build()).build();
    }

    @Override
    protected ScheduledSessionUserDao getDao() {
        return scheduledSessionUserDao;
    }

    @Override
    public void verifySave() {
        assertThat(findById(1).getUserProfile().getUserId(),equalTo(getSingleEntity().getUserProfile().getUserId()));
    }
}

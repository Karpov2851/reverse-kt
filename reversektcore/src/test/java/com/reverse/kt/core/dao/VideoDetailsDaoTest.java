package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.VideoDetails;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 05-06-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class VideoDetailsDaoTest extends BaseTestDao<VideoDetails,VideoDetailsDao,Integer>{

    @Inject
    private VideoDetailsDao videoDetailsDao;


    @Override
    protected VideoDetails getSingleEntity() {
        return VideoDetails.builder().videoDetails("Test video detail desc").videoDirectoryPath("abc/test").
                videoDirectoryRefCd("asfasgsaasga").videoName("my video").build();
    }

    @Override
    protected VideoDetailsDao getDao() {
        return videoDetailsDao;
    }

    @Override
    public void verifySave() {
        assertThat(findById(1).getVideoName(),equalTo(getSingleEntity().getVideoName()));
    }
}

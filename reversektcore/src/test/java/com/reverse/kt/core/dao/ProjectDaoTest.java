package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
/**
 * Created by vikas on 09-05-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProjectDaoTest extends BaseTestDao<Project,ProjectDao,Integer>{

    @Inject
    private ProjectDao projectDao;

    @Test
    @Sql(scripts = {"classpath:db/project_data.sql"})
    public void fetchProjectForBusinessUnitTest() throws Exception{
        //when
        List<Project> actual = projectDao.fetchProjectForBusinessUnit(1);

        //then
        assertNotNull(actual);
        assertThat(actual,hasSize(2));
        assertThat(actual.stream().findFirst().get().getProjectItems(),hasSize(2));
    }

    @Override
    protected Project getSingleEntity() {
        return Project.builder().projectDesc("project test desc").projectName("SAMPLE_PROJECT").build();
    }

    @Override
    protected ProjectDao getDao() {
        return projectDao;
    }

    @Override
    @Test
    public void verifySave() {
        assertThat(findById(1).getProjectName(),equalTo(getSingleEntity().getProjectName()));
    }
}

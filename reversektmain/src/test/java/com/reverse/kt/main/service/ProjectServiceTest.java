package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.ProjectDao;
import com.reverse.kt.core.dao.ProjectItemDao;
import com.reverse.kt.core.model.Project;
import com.reverse.kt.core.model.ProjectItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 12-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private ProjectDao projectDao;

    @Mock
    private ProjectItemDao projectItemDao;

    @InjectMocks
    private ProjectService projectService;

    @Captor
    private ArgumentCaptor<Integer> captorInteger;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchProjectForBusinessUnitTestShouldSucceed() throws Exception{
        //when
        when(projectDao.fetchProjectForBusinessUnit(captorInteger.capture())).thenReturn(getProjectTelatedRecords());

        //then
        List<Project> projects = projectService.fetchProjectForBusinessUnit(10);
        assertNotNull(projects);
        assertThat(captorInteger.getValue(),equalTo(10));
    }

    @Test
    public void fetchProjectForIdTestShouldSucceed() throws Exception{
        //when
        when(projectDao.findById(captorInteger.capture())).thenReturn(getProjectTelatedRecords().get(0));

        //then
        Project project = projectService.fetchProjectForId(10);
        assertNotNull(project);
        assertThat(captorInteger.getValue(),equalTo(10));
    }

    @Test
    public void fetchProjectItemForProjectItemSeqShouldSucceed() throws Exception{
        //when
        when(projectItemDao.findById(captorInteger.capture())).thenReturn(ProjectItem.builder().projectItemDesc("Project item desc").build());

        //then
        ProjectItem projectItem = projectService.fetchProjectItemForProjectItemSeq(10);
        assertNotNull(projectItem);
        assertThat(captorInteger.getValue(),equalTo(10));
    }

    private List<Project> getProjectTelatedRecords(){
        return Arrays.asList(
                Project.builder().projectName("PROJECT_NAME_ONE").projectDesc("Project name des").build(),
                Project.builder().projectName("PROJECT_NAME_TWO").projectDesc("Project name two des").build()
        );
    }
}

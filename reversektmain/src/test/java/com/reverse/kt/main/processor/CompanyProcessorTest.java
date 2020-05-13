package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.*;
import com.reverse.kt.main.service.ProjectService;
import com.reverse.kt.main.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 09-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyProcessorTest {

    private static final int USER_PROFILE_SEQ = 1;


    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<Integer> integerCaptor;

    @InjectMocks
    private CompanyProcessor companyProcessor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void fetchCompanyRelatedEntityTestForProjects() throws Exception{
        //given
        Map<String,Object> mapper = new HashMap<String,Object>(){{
            put("bu","BU_ONE");
        }};
        UserProfile getUserWithCompanyDetails = getUserWithCompanyDetails();

        //when
        when(userService.fetchUserByProfileSeq(integerCaptor.capture())).thenReturn(getUserWithCompanyDetails);
        when(projectService.fetchProjectForBusinessUnit(anyInt())).thenReturn(new ArrayList<>(getUserWithCompanyDetails.getCompanyMstr().getProjects()));
        //then

        Map<Object,Object> actual = companyProcessor.fetchCompanyRelatedEntity(USER_PROFILE_SEQ,mapper, CompanyProcessor.OperationOps.PROJ);
        assertNotNull(actual);
        assertThat(actual.size(),is(2));
        assertThat(integerCaptor.getValue(),equalTo(USER_PROFILE_SEQ));
    }

    @Test
    public void fetchCompanyRelatedEntityTestForProjectItems() throws Exception{
        //given
        Map<String,Object> mapper = new HashMap<String,Object>(){{
            put("projectSeq",1);
        }};
        UserProfile getUserWithCompanyDetails = getUserWithCompanyDetails();

        //when
        when(userService.fetchUserByProfileSeq(integerCaptor.capture())).thenReturn(getUserWithCompanyDetails);
        when(projectService.fetchProjectForId(1)).thenReturn(getUserWithCompanyDetails.getCompanyMstr().getProjects().stream().filter(t->t.getProjectSeq().equals(1)).findFirst().get());

        //then
        Map<Object,Object> actual = companyProcessor.fetchCompanyRelatedEntity(USER_PROFILE_SEQ,mapper, CompanyProcessor.OperationOps.PROJITEM);
        assertNotNull(actual);
        assertThat(actual.size(),is(2));
        assertThat(integerCaptor.getValue(),equalTo(USER_PROFILE_SEQ));

    }



    private UserProfile getUserWithCompanyDetails(){

        CompanyMstr comp1  = CompanyMstr.builder().companyCd("COMP1_CD").companyMstrSeq(1).build();
        DesignationMstr designation = DesignationMstr.builder().companyMstr(comp1).designatioName("Designation Name").priority(1).build();

        BusinessUnit businessUnitOne = BusinessUnit.builder().businessUnitSeq(1).businessUnitName("BUCOMP ONE").businessCd("BU_ONE").companyMstr(comp1).build();
        BusinessUnit businessUnitTwo = BusinessUnit.builder().businessUnitSeq(2).businessUnitName("BUCOMP TWO").businessCd("BU_TWO").companyMstr(comp1).build();

        Project buProjectOne = Project.builder().projectSeq(1).businessUnit(businessUnitOne).projectName("Project BU One").projectDesc("Project BU One desc").companyMstr(comp1).build();
        Project buProjectTwo = Project.builder().projectSeq(2).businessUnit(businessUnitTwo).projectName("Project BU Two").projectDesc("Project BU Two desc").companyMstr(comp1).build();

        ProjectItem projectItemProjectOne = ProjectItem.builder().projectItemSeq(1).projectItemDesc("Project item project one").build();
        projectItemProjectOne.setProject(buProjectOne);
        ProjectItem projectItemProjectOneTwo = ProjectItem.builder().projectItemSeq(2).projectItemDesc("Project item project one nor").build();
        projectItemProjectOne.setProject(buProjectOne);
        ProjectItem projectItemProjectTwoOne = ProjectItem.builder().projectItemSeq(3).projectItemDesc("Project item project two dgg").build();
        projectItemProjectOne.setProject(buProjectTwo);
        ProjectItem projectItemProjectTwoTwo = ProjectItem.builder().projectItemSeq(3).projectItemDesc("Project item project two dgg").build();
        projectItemProjectOne.setProject(buProjectTwo);

        buProjectOne.getProjectItems().add(projectItemProjectOne);
        buProjectOne.getProjectItems().add(projectItemProjectOneTwo);
        buProjectTwo.getProjectItems().add(projectItemProjectTwoOne);
        buProjectTwo.getProjectItems().add(projectItemProjectTwoTwo);

        comp1.getBusinessUnits().add(businessUnitOne);
        comp1.getBusinessUnits().add(businessUnitTwo);
        comp1.getDesignationMstrs().add(designation);

        comp1.getProjects().add(buProjectOne);
        comp1.getProjects().add(buProjectTwo);

        return UserProfile.builder().userId("user_id").companyMstr(comp1).build();
    }


}

package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.*;
import com.reverse.kt.core.ui.UploadLectureView;
import com.reverse.kt.main.common.BaseMainTest;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.ProjectService;
import com.reverse.kt.main.service.VideoService;
import com.reverse.kt.main.util.MainCommonUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 18-06-2020.
 */

public class UploadProcessorTest extends BaseMainTest{

    private static final Integer userProfileSeq = 5;

    @Mock
    private VideoService videoService;

    @Mock
    private MainCommonUtil mainCommonUtil;

    @Mock
    private ProjectService projectService;

    @Mock
    private Environment environment;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private UploadProcessor uploadProcessor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<VideoDetails> videoDetailCaptor;

    @Captor
    private ArgumentCaptor<Boolean> booleanCaptor;


    @Test
    public void uploadLectureVideoDetailsShouldThrowExceptionForNoFile() throws Exception{
        //then
        expectedException.expect(NullPointerException.class);

        //given
        UploadLectureView uploadLectureView = mockedUploadLectureViewDataSet(5,null);

        //when
        uploadProcessor.uploadLectureVideoDetails(uploadLectureView);

    }

    @Test
    public void uploadLectureVideoDetailsShouldThrowExceptionForEditWithoutAndVideoDetailRecord() throws Exception{
        //then
        expectedException.expect(NullPointerException.class);

        //given
        UploadLectureView uploadLectureView = mockedUploadLectureViewDataSet(5,null);


        //when
        when(videoService.fetchVideoDetailsById(integerArgumentCaptor.capture())).thenReturn(null);
        uploadProcessor.uploadLectureVideoDetails(uploadLectureView);
    }

    @Test
    public void uploadLectureVideoDetailsShouldForNewVideoShouldSucceed() throws Exception{
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("videoFile",new FileInputStream(getDummyFile()));
        UploadLectureView uploadLectureView  = mockedUploadLectureViewDataSet(null,mockMultipartFile);
        when(projectService.fetchProjectItemForProjectItemSeq(integerArgumentCaptor.capture())).thenReturn(fetchMockProjectItem());
        when(environment.getProperty(anyString())).thenReturn("/dummypath/");
        doNothing().when(videoService).saveVideoDetails(videoDetailCaptor.capture(),booleanCaptor.capture());

        //when
        uploadProcessor.uploadLectureVideoDetails(uploadLectureView);

        //then
        assertThat(integerArgumentCaptor.getValue(),equalTo(fetchMockProjectItem().getProjectItemSeq()));
        assertFalse(booleanCaptor.getValue());
        assertThat(videoDetailCaptor.getValue().getVideoName(),equalTo(uploadLectureView.getVideoName()));
        assertThat(videoDetailCaptor.getValue().getSkillMstrSeqList(),equalTo(uploadLectureView.getSkillMstrSelected()));
    }

    @Test
    public void uploadLectureVideoDetailsShouldExistingVideoShouldSucceed() throws Exception{
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("videoFile",new FileInputStream(getDummyFile()));
        UploadLectureView uploadLectureView  = mockedUploadLectureViewDataSet(5,mockMultipartFile);
        when(projectService.fetchProjectItemForProjectItemSeq(integerArgumentCaptor.capture())).thenReturn(fetchMockProjectItem());
        when(environment.getProperty(anyString())).thenReturn("/dummypath/");
        when(videoService.fetchVideoDetailsById(mockVideoDetails().getVideoDetailSeq())).thenReturn(mockVideoDetails());
        doNothing().when(videoService).saveVideoDetails(videoDetailCaptor.capture(),booleanCaptor.capture());

        //when
        uploadProcessor.uploadLectureVideoDetails(uploadLectureView);

        //then
        assertThat(integerArgumentCaptor.getValue(),equalTo(fetchMockProjectItem().getProjectItemSeq()));
        assertTrue(booleanCaptor.getValue());
    }

    @Test
    public void fetchUploadViewDetailsShouldThrowExceptionForNoEmployeeRecord() throws Exception{
        //then
        expectedException.expect(NullPointerException.class);

        //given
        when(employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq)).thenReturn(null);

        //when
        uploadProcessor.fetchUploadViewDetails(10,userProfileSeq);

    }

    @Test
    public void fetchUploadViewDetailsShouldThrowExceptionForNoVideDetailsForEditOption() throws Exception{
        //then
        expectedException.expect(NullPointerException.class);

        //given
        when(employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq)).thenReturn(EmployeeMstr.builder().build());
        when(videoService.fetchVideoDetailsById(10)).thenReturn(null);

        //when
        uploadProcessor.fetchUploadViewDetails(10,userProfileSeq);

    }

    @Test
    public void fetchUploadViewDetailsShouldSucceedForNonEditOption() throws Exception {
        //given
        EmployeeMstr actualEmployeeMstr = EmployeeMstr.builder().employeeFirstName("test first name").projectItem(fetchMockProjectItem()).build();

        //when
        when(employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq)).thenReturn(actualEmployeeMstr);
        UploadLectureView actualDataSet = uploadProcessor.fetchUploadViewDetails(null,userProfileSeq);

        //then
        assertTrue(actualDataSet.getProjectItemDropDown().containsKey("my project desc"));
        assertThat(actualDataSet.getProjectItemDropDown().get("my project desc"),equalTo(mockProjectItemDropDown().get("my project desc")));
    }

    @Test
    public void fetchUploadViewDetailsShouldSucceedForEditOption() throws Exception {
        //given
        EmployeeMstr actualEmployeeMstr = EmployeeMstr.builder().employeeFirstName("test first name").projectItem(fetchMockProjectItem()).build();

        //when
        when(employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq)).thenReturn(actualEmployeeMstr);
        when(videoService.fetchVideoDetailsById(integerArgumentCaptor.capture())).thenReturn(mockVideoDetails());
        UploadLectureView actualDataSet = uploadProcessor.fetchUploadViewDetails(10,userProfileSeq);

        //then
        assertThat(integerArgumentCaptor.getValue(),equalTo(10));
        assertTrue(actualDataSet.getProjectItemDropDown().containsKey("my project desc"));
        assertThat(actualDataSet.getProjectItemDropDown().get("my project desc"),equalTo(mockProjectItemDropDown().get("my project desc")));
        assertThat(actualDataSet.getVideoName(),equalTo(mockVideoDetails().getVideoName()));
    }


    private UploadLectureView mockedUploadLectureViewDataSet(Integer videoDetailSeq,MockMultipartFile mockMultipartFile){
        return UploadLectureView.builder().videoName("new video test").videoDetails("new video test details").videoFileUpload(mockMultipartFile)
                .skillMstrSelected("1,2,5").projectItemSelected("10").videoDetailSeq(videoDetailSeq).build();
    }

    private ProjectItem fetchMockProjectItem(){
        Project employeeProject = new Project();
        ProjectItemSkill projectItemSkill = new ProjectItemSkill();
        Set<ProjectItemSkill> projectItemSkills = new HashSet<>();
        ProjectItem projectItem = ProjectItem.builder().projectItemDesc("my project desc").projectItemSeq(10).build();
        employeeProject.setProjectItems(new HashSet<>(Collections.singletonList(projectItem)));
        projectItemSkill.setProjectItem(projectItem);
        projectItemSkills.add(projectItemSkill);
        projectItem.setProject(employeeProject);
        projectItem.setProjectItemSkills(projectItemSkills);
        return projectItem;
    }

    private VideoDetails mockVideoDetails(){
        VideoDetails videoRecord =  VideoDetails.builder().videoDetailSeq(5).videoName("test_video")
                .videoDetails("test_video_details").videoDirectoryPath("/videopath/").videoDirectoryRefCd("videorefcd").build();
        videoRecord.setProjectItem(fetchMockProjectItem());
        return videoRecord;
    }

    private Map<String,Integer> mockProjectItemDropDown(){
        return new HashMap<String,Integer>(){{
            put("my project desc",10);
        }};
    }

}

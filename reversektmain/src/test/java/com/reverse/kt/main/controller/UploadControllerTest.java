package com.reverse.kt.main.controller;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.ui.UploadLectureView;
import com.reverse.kt.main.common.BaseMainTest;
import com.reverse.kt.main.processor.UploadProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by vikas on 16-06-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest extends BaseMainTest{

    private static final Integer integerSeq = 10;

    private MockMvc mockMvc;

    @Mock
    private UploadProcessor uploadProcessor;

    @InjectMocks
    private UploadController uploadController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(uploadController).build();
    }

    @Test
    public void loadUploadViewTestShouldSucceed() throws Exception {
        //given
        UploadLectureView uploadLectureView = mockedUploadLectureViewDataSet(integerSeq,null);
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,integerSeq);

        //when
        when(uploadProcessor.fetchUploadViewDetails(null,integerSeq))
                .thenReturn(uploadLectureView);


        //then
        this.mockMvc.perform(get("/load-upload").session(httpSession))
                .andExpect(view().name("upload_view"))
                .andExpect(model().attributeExists("uploadLectureView"))
                .andExpect(model().attribute("uploadLectureView",hasProperty("videoName",is("new video test"))));
    }

    @Test
    public void loadUploadViewTestWhenThrowsExceptionValidateErrorMessage() throws Exception{
        //given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,integerSeq);

        //when
        doThrow(IllegalArgumentException.class).when(uploadProcessor).fetchUploadViewDetails(integerSeq,integerSeq);

        //then
        this.mockMvc.perform(get("/load-upload").session(httpSession))
                .andExpect(view().name("upload_view"))
                .andExpect(model().attributeExists("uploadLectureView"))
                .andExpect(model().attribute("uploadLectureView",hasProperty("message",is("Something went wrong"))));
    }

    @Test
    public void saveUploadViewShouldSucceed() throws Exception{
        //given
        MockMultipartFile dummyFile = new MockMultipartFile("videoFileUpload",new FileInputStream(getDummyFile()));
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,integerSeq);


        //when
        doNothing().when(uploadProcessor).uploadLectureVideoDetails(mockedUploadLectureViewDataSet(integerSeq,null));
        when(uploadProcessor.fetchUploadViewDetails(null,integerSeq))
                .thenReturn(mockedUploadLectureViewDataSet(integerSeq,null));

        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/save-upload").file(dummyFile)
                .session(httpSession);
        mockMvc.perform(builder).andExpect(view().name("upload_view"))
                .andExpect(model().attributeExists("uploadLectureView"))
                .andExpect(model().attribute("uploadLectureView",hasProperty("message",is("Sucessfully uploaded video"))));
    }

    private UploadLectureView mockedUploadLectureViewDataSet(Integer videoDetailSeq, MockMultipartFile mockMultipartFile){
        return UploadLectureView.builder().videoName("new video test").videoDetails("new video test details").videoFileUpload(mockMultipartFile)
                .skillMstrSelected("1,2,5").projectItemSelected("10").videoDetailSeq(videoDetailSeq).build();
    }

}

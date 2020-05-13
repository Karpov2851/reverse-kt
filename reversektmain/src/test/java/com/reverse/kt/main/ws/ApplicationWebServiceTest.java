package com.reverse.kt.main.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.ui.UiWebServiceRequest;
import com.reverse.kt.main.processor.CompanyProcessor;
import com.reverse.kt.main.processor.UserProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by vikas on 09-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationWebServiceTest {

    private static final int USER_PROFILE_SEQ = 1;

    public static final String DUMMY_SOURCE_IMAGE = "images/dummy.jpg";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private CompanyProcessor companyProcessor;

    @Mock
    private UserProcessor userProcessor;

    @InjectMocks
    private ApplicationWebService applicationWebService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(applicationWebService).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void fetchCompanyRelatedEntitiesTestBusinessUnitShouldSucceed() throws Exception{
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,USER_PROFILE_SEQ);
        Map<String,Object> requestParameters = new HashMap<String,Object>(){{
            put("bu","BU_CD_ONE");
        }};
        Map<Object,Object> resultMap = getResultMapForCompanyRelatedEntity(CompanyProcessor.OperationOps.PROJ);

        String requestBody = objectMapper.writeValueAsString(new UiWebServiceRequest(requestParameters,"PROJ"));
        String expected  = objectMapper.writeValueAsString(getResultMapForCompanyRelatedEntity(CompanyProcessor.OperationOps.PROJ));

        //when
        when(companyProcessor.fetchCompanyRelatedEntity(USER_PROFILE_SEQ,requestParameters,CompanyProcessor.OperationOps.PROJ)).
                thenReturn(resultMap);

        //then
        MvcResult result = mockMvc.perform(post("/ws/ddown").session(session).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThat(expected,equalTo(result.getResponse().getContentAsString()));
    }

    @Test
    public void getImageAsResponseEntityUnitTestShouldSucceed() throws Exception{
        //given

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,USER_PROFILE_SEQ);

        //when
        when(userProcessor.fetchUserByProfileSeq(USER_PROFILE_SEQ)).thenReturn(profileRecord());

        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ws/fetch-image")
                .session(session).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andReturn();
        assertNotNull(result.getResponse());

    }

    @Test
    public void saveImageTestShouldSucceed() throws Exception{
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imageFile",new FileInputStream(getDummyFile()));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,USER_PROFILE_SEQ);

        //when
        when(userProcessor.fetchUserByProfileSeq(USER_PROFILE_SEQ)).thenReturn(profileRecord());
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/ws/saveimage").file(mockMultipartFile)
                .session(session);
        MvcResult result = mockMvc.perform(builder).andReturn();
        assertNotNull(result.getResponse());

    }

    private Map<Object,Object> getResultMapForCompanyRelatedEntity(CompanyProcessor.OperationOps op){
        switch(op){
            case BU:
                return new HashMap<Object,Object>(){{
                    put("BU_CD_ONE","BU_NAME_ONE");
                    put("BU_CD_TWO","BU_NAME_TWO");

                }};

            case PROJ:
                return new HashMap<Object,Object>(){{
                    put(1,"PROJECT_NAME_ONE");
                    put(2,"PROJECT_NAME_TWO");
                }};

            case PROJITEM:
                return new HashMap<Object,Object>(){{
                    put(1,"PROJECT__ITEM_NAME_ONE");
                    put(2,"PROJECT__ITEM_NAME_TWO");
                }};

        }

        return null;
    }

    private File getDummyFile(){
        File file = new File(
                getClass().getClassLoader().getResource(DUMMY_SOURCE_IMAGE).getFile()
        );
        return file;
    }

    private UserProfile profileRecord(){
        return UserProfile.builder().userId("user_id").userProfileImagePath(getDummyFile().getAbsolutePath()).build();
    }

}

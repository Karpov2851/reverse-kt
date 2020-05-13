package com.reverse.kt.main.controller;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.ui.RegistrationModelView;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by vikas on 08-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserProcessor userProcessor;

    @InjectMocks
    private ApplicationController applicationController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
    }

    @Test
    public void loadProfileTest() throws Exception{
        //given
        RegistrationModelView registrationModelView = generateLoadProfileModelView();
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,12);

        //when
        when(userProcessor.fetchViewProfileDetails(12)).
                thenReturn(registrationModelView);



        //then
        mockMvc.perform(get("/load-profile").session(httpSession)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("view_profile"))
                .andExpect(model().attribute("regVO",hasProperty("firstName",is("first name"))));
    }

    @Test
    public void historyShouldSucceed() throws Exception{
        //then
        mockMvc.perform(get("/load-history")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("history"));
    }

    @Test
    public void saveProfileTestSucceed() throws Exception{
        //given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,12);


        //when
        doNothing().when(userProcessor).updateEmployeeDetails(12,generateSaveProfileModelView());
        when(userProcessor.fetchViewProfileDetails(12)).
                thenReturn(generateLoadProfileModelView());

        //then
        mockMvc.perform(post("/save-profile").session(httpSession).flashAttr("rv",generateSaveProfileModelView())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/load-profile"))
                .andExpect(flash().attributeExists("rv"))
                .andExpect(flash().attribute("rv",hasProperty("showError",is(false))));
    }


    @Test
    public void saveProfileTestShouldThrowError() throws Exception{
        //given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(ApplicationConstants.USER_PROFILE_SEQ,12);

        //when
        doThrow(Exception.class).when(userProcessor).updateEmployeeDetails(12,generateSaveProfileModelView());
        when(userProcessor.fetchViewProfileDetails(12)).
                thenReturn(generateLoadProfileModelView());

        //then
        mockMvc.perform(post("/save-profile").session(httpSession)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/load-profile"))
                .andExpect(flash().attributeExists("rv"))
                .andExpect(flash().attribute("rv",hasProperty("showError",is(true))));
    }


    private RegistrationModelView generateLoadProfileModelView(){
        RegistrationModelView rv = new RegistrationModelView();
        rv.setCompanyDropDown(new HashMap<String,String>(){{
            put("company1","company_one_name");
            put("company2","company_two_name");
        }});
        rv.setFirstName("first name");
        return rv;
    }

    private RegistrationModelView generateSaveProfileModelView(){
        RegistrationModelView rv = new RegistrationModelView();
        rv.setProjectItemSelected("1");
        rv.setBusinessUnitSelected("BU_CD");
        rv.setProjectSelected("12");
        rv.setUserName("userName");
        return rv;
    }
}


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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by vikas on 08-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HttpSession httpSession;

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
    @WithMockUser(roles = {"ROLE_ADMIN"})
    public void loadProfileTest() throws Exception{
        //given
        RegistrationModelView registrationModelView = generateModelView();

        //when
        when(httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ)).thenReturn(12);
        when(userProcessor.fetchViewProfileDetails(12)).
                thenReturn(registrationModelView);


        //then
        mockMvc.perform(get("/load-profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("view_profile"))
                .andExpect(model().attribute("regVO",hasProperty("firstName",is("first name"))));
    }

    private RegistrationModelView generateModelView(){
        RegistrationModelView rv = new RegistrationModelView();
        rv.setCompanyDropDown(new HashMap<String,String>(){{
            put("company1","company_one_name");
            put("company2","company_two_name");
        }});
        rv.setFirstName("first name");
        return rv;
    }
}


package com.reverse.kt.main.controller;

import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.processor.LoginProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by vikas on 24-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginProcessor loginProcessor;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loadRegisterShouldSucceed() throws Exception{
        //given
        RegistrationModelView registrationModelView = generateModelView();

        //when
        when(loginProcessor.generateRegistrationModelView()).
                thenReturn(registrationModelView);

        //then
        mockMvc.perform(get("/load-register").flashAttr("rv",new RegistrationModelView())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("regVO",hasProperty("companyDropDown",hasKey("company1"))));

    }


    private RegistrationModelView generateModelView(){
        RegistrationModelView rv = new RegistrationModelView();
        rv.setCompanyDropDown(new HashMap<String,String> (){{
            put("company1","company_one_name");
            put("company2","company_two_name");
        }});
        return rv;
    }
}

package com.reverse.kt.main.security;

import com.reverse.kt.main.config.CoreMainConfigTest;
import com.reverse.kt.main.processor.LoginProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by vikas on 17-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreMainConfigTest.class)
@WebAppConfiguration
@Sql("classpath:db/web_security.sql")
public class WebSecurityTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private LoginProcessor loginProcessor;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
         .apply(springSecurity())
         .build();
    }

    @Test
    public void serverCalloutShouldSucceed() throws Exception{
        mockMvc.perform(get("/server").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @WithMockUser(username = "user_id",roles = {"ADMIN"})
    @Test
    public void serverAuthenticatedCalloutShouldGive200Status() throws Exception{
        mockMvc.perform(get("/server-authenticated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user_id",roles = {"EMPLOYEE"})
    @Test
    public void serverAuthenticatedCalloutShouldRedirect() throws Exception{
        mockMvc.perform(get("/server-authenticated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void loginShouldShouldSucceedWithRedirection() throws Exception{
        mockMvc.perform(post("/j_spring_security_check").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user_id").param("password","vikas@123")).andExpect(redirectedUrl("/home"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginShouldShowErrorMessage() throws Exception{
        mockMvc.perform(get("/login?error=true").contentType(MediaType.APPLICATION_JSON)).andExpect(view().name("login"))
                .andExpect(model().attribute("regVO",hasProperty("showError",is(true))))
                .andExpect(model().attribute("regVO",hasProperty("message",is("Incorrect user id or password."))));
    }

    @Test
    public void loginShouldShowLogoutMessage() throws Exception{
        mockMvc.perform(get("/login?logout=Y").contentType(MediaType.APPLICATION_JSON)).andExpect(view().name("login"))
                .andExpect(model().attribute("regVO",hasProperty("showSuccess",is(true))))
                .andExpect(model().attribute("regVO",hasProperty("message",is("User logged out successfully"))));
    }
}

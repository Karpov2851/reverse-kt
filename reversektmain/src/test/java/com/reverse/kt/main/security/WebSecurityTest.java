package com.reverse.kt.main.security;

import com.reverse.kt.core.dao.UserProfileDao;
import com.reverse.kt.main.config.CoreMainConfigTest;
import com.reverse.kt.main.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by vikas on 17-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreMainConfigTest.class)
@WebAppConfiguration
public class WebSecurityTest {

    @Mock
    private UserProfileDao userProfileDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Inject
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void test(){

    }
}

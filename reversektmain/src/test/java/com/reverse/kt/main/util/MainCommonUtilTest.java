package com.reverse.kt.main.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by vikas on 06-05-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class MainCommonUtilTest {

    @Configuration
    static class ContextConfiguration{
            @Bean
            public PasswordEncoder passwordEncoder(){
                return new BCryptPasswordEncoder();
            }

            @Bean
            public MainCommonUtil mainCommonUtil(){
                MainCommonUtil mc = new MainCommonUtil();
                mc.setPasswordEncoder(passwordEncoder());
                return mc;
            }
    }

    @Inject
    private MainCommonUtil mainCommonUtil;


    @Test
    public void convertToBecryptTestShouldSucceed(){
        //given
        String pwd = "testpassword";

        //when
        String actualValue = mainCommonUtil.convertToBecrypt(pwd);

        //then
        assertNotNull(actualValue);
        assertTrue(mainCommonUtil.getPasswordEncoder().matches(pwd,actualValue));
    }
}

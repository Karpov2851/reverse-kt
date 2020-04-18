package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.UserProfileDao;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.main.security.CustomUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 16-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserProfileDao userProfileDao;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<String> captorString;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameTestShouldSucceed() throws Exception{
        //given
        UserProfile userProfile = UserProfile.builder().password("pwd").userId("username").userRole(UserRole.builder().userRoleCd("ROLE_TEST").build()).build();
        CustomUser expected = CustomUser.builder().password("pwd").username("username").build();

        //when
        when(userProfileDao.fetchUserProfileByUserId(captorString.capture())).thenReturn(userProfile);

        //then
        UserDetails userDetails = userService.loadUserByUsername("username");
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUser);
        assertThat(userDetails.getPassword(),equalTo(expected.getPassword()));
        assertThat(userDetails.getUsername(),equalTo(expected.getUsername()));
        assertEquals(expected.getUsername(),captorString.getValue());
    }

}

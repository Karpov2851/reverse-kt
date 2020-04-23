package com.reverse.kt.main.service;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.dao.UserProfileDao;
import com.reverse.kt.core.dao.UserRoleDao;
import com.reverse.kt.core.model.CompanyMstr;
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
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 16-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserProfileDao userProfileDao;

    @Mock
    private UserRoleDao userRoleDao;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<String> captorString;

    @Captor
    private ArgumentCaptor<UserRoleIdentifier> captorRoleIdentifier;

    @Captor
    private ArgumentCaptor<UserProfile> captorBean;

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
    }


    @Test
    public void fetchUserForRoleAndCompanyTestShouldSucceed() throws Exception{
        //given
        UserRole expected = UserRole.builder().userRoleCd(UserRoleIdentifier.ADMIN.name()).companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();

        //when
        when(userRoleDao.fetchUserForRoleAndCompany(captorRoleIdentifier.capture(),anyInt())).thenReturn(expected);

        //then
        UserRole actual = userService.fetchUserForRoleAndCompany(UserRoleIdentifier.ADMIN,1);
        assertNotNull(actual);
        assertThat(actual.getUserRoleCd(),equalTo(expected.getUserRoleCd()));
        assertEquals(UserRoleIdentifier.ADMIN,captorRoleIdentifier.getValue());
    }

    @Test
    public void createUserProfileTest() throws Exception{
        //given
        UserProfile userProfile = UserProfile.builder().password("pwd").userId("username").userRole(UserRole.builder().userRoleCd("ROLE_TEST").build()).build();

        //when
        doNothing().when(userProfileDao).saveOrUpdateEntity(captorBean.capture(),anyBoolean());

        //then
        userService.createUserProfile(userProfile,true);
        assertThat(userProfile.getUserId(),equalTo(captorBean.getValue().getUserId()));

    }

}

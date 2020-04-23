package com.reverse.kt.main.processor;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.CompanyService;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.UserService;
import com.reverse.kt.main.util.MainCommonUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 23-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginProcessorTest {

    @Captor
    private ArgumentCaptor<UserProfile> userProfileArgumentCaptor;

    @Captor
    private ArgumentCaptor<EmployeeMstr> employeeMstrArgumentCaptor;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @Mock
    private MainCommonUtil mainCommonUtil;

    @InjectMocks
    private LoginProcessor loginProcessor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUserWithNullRegistrationModelShouldThrowException() throws Exception{
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("RegistrationModelView is null in registerUser method");
        RegistrationModelView rv = null;

        //when
        loginProcessor.registerUser(rv);
    }

    @Test
    public void registerUserWithNoUserRoleShouldThrowException() throws Exception{
        //given
        expectedException.expect(Exception.class);
        expectedException.expectMessage("User role does not exist");
        RegistrationModelView rv = RegistrationModelView.builder().userName("user").companySelected("TEST_COMP").build();
        CompanyMstr existingCompany = CompanyMstr.builder().companyCd("TEST_COMP").companyMstrSeq(1).build();

        //when
        when(companyService.fetchCompanyByCompanyCd(anyString())).thenReturn(existingCompany);
        when(userService.fetchUserForRoleAndCompany(UserRoleIdentifier.EMPLOYEE,existingCompany.getCompanyMstrSeq())).thenReturn(null);
        loginProcessor.registerUser(rv);
    }

    @Test
    public void registerUserWithExistingUser() throws Exception{
        //given
        RegistrationModelView rv = RegistrationModelView.builder().userName("user").build();
        UserProfile existingUser = UserProfile.builder().userId("userId").password("pwd").build();
        //when
        when(userService.fetchUserProfileByUserId(anyString())).thenReturn(existingUser);

        //then
        rv = loginProcessor.registerUser(rv);
        assertTrue(rv.isShowError());
        assertThat(rv.getMessage(),equalTo("User already exists with this username"));

    }


    @Test
    public void registerUserShouldSucceed() throws Exception{
        //given
        RegistrationModelView rv = RegistrationModelView.builder().userName("user").pwd("pwd").firstName("firstname").companySelected("TEST_COMP").build();
        CompanyMstr existingCompany = CompanyMstr.builder().companyCd("TEST_COMP").companyMstrSeq(1).build();
        UserRole userRole =  UserRole.builder().userRoleCd("ROLE_CD").build();

        //when
        doNothing().when(userService).createUserProfile(userProfileArgumentCaptor.capture(),anyBoolean());
        doNothing().when(employeeService).createEmployeeRecord(employeeMstrArgumentCaptor.capture(),anyBoolean());

        when(userService.fetchUserProfileByUserId(anyString())).thenReturn(null);
        when(companyService.fetchCompanyByCompanyCd(anyString())).thenReturn(existingCompany);
        when(userService.fetchUserForRoleAndCompany(UserRoleIdentifier.EMPLOYEE,existingCompany.getCompanyMstrSeq())).thenReturn(userRole);
        when(mainCommonUtil.convertToBecrypt(rv.getPwd())).thenReturn("encryptedpwd");

        //then
        rv = loginProcessor.registerUser(rv);
        assertThat("encryptedpwd",equalTo(userProfileArgumentCaptor.getValue().getPassword()));
        assertThat(rv.getFirstName(),equalTo(employeeMstrArgumentCaptor.getValue().getEmployeeFirstName()));
        assertTrue(rv.isShowSuccess());
    }

    @Test
    public void generateRegistrationModelViewShouldReturnNull() throws Exception{
        //given
        List<CompanyMstr> allCompanies = null;

        //when
        when(companyService.fetchAllActiveCompanies()).thenReturn(allCompanies);

        //then
        RegistrationModelView rv = loginProcessor.generateRegistrationModelView();
        assertNull(rv);

    }

    @Test
    public void generateRegistrationModelViewShouldSucceed() throws Exception{
        //given
        List<CompanyMstr> allCompanies = Arrays.asList(CompanyMstr.builder().companyCd("TEST_ONE_CD").companyName("TEST_COMP").build(),
                CompanyMstr.builder().companyCd("TEST_TWO_CD").companyName("TEST_COMP_TWO").build(),
                CompanyMstr.builder().companyCd("TEST_THREE_CD").companyName("TEST_COMP_THREE").build());

        //when
        when(companyService.fetchAllActiveCompanies()).thenReturn(allCompanies);

        //then
        RegistrationModelView rv = loginProcessor.generateRegistrationModelView();
        assertNotNull(rv);
        assertThat(rv.getCompanyDropDown(),hasKey("TEST_TWO_CD"));

    }


}

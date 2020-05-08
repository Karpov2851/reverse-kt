package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.CompanyService;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 08-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProcessorTest {

    @Mock
    private Environment environment;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private UserProcessor userProcessor;

    @Captor
    private ArgumentCaptor<Integer> captorInteger;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchViewProfileDetailsTestShouldThrowException() throws Exception{
        //given
        EmployeeMstr employeeMstr = getEmployeeMstrRecord();
        expectedException.expect(Exception.class);
        expectedException.expectMessage("User doest not exist");

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(null);

        //then
        userProcessor.fetchViewProfileDetails(12);
        assertThat(captorInteger.getValue(),equalTo(12));
    }

    @Test
    public void fetchViewProfileDetailsTestShouldSucceed() throws Exception{
        //given
        EmployeeMstr employeeMstr = getEmployeeMstrRecord();

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(UserProfile.builder().build());
        when(employeeService.fetchEmployeeForUserProfileSeq(captorInteger.capture())).thenReturn(employeeMstr);
        when(companyService.fetchMapOfCompanyCdAndCompanyName()).thenReturn(getCompanyMapData());

        //then
        RegistrationModelView registrationModelView = userProcessor.fetchViewProfileDetails(12);
        assertThat(captorInteger.getValue(),equalTo(12));
        assertNotNull(registrationModelView.getCompanyDropDown());
        assertThat(registrationModelView.getFirstName(),equalTo(employeeMstr.getEmployeeFirstName()));
        assertThat(registrationModelView.getLastName(),equalTo(employeeMstr.getEmployeeLastName()));
    }




    private EmployeeMstr getEmployeeMstrRecord(){
        return EmployeeMstr.builder().employeeFirstName("firstname").employeeLastName("lastname").
                companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();
    }

    private Map<String,String> getCompanyMapData(){
        return new HashMap<String,String>(){{
            put("TEST_ONE_CD","TEST_COMP");
            put("TEST_TWO_CD","TEST_COMP_TWO");
            put("TEST_THREE_CD","TEST_COMP_THREE");
        }};
    }
}

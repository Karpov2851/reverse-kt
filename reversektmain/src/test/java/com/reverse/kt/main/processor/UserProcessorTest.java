package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.*;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.*;
import com.reverse.kt.main.util.MainCommonUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by vikas on 08-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProcessorTest {

    private static final String DUMMY_SOURCE_IMAGE = "images/dummy.jpg";
    private static final String DUMMY_PATH_TO_SAVE = "D:/Reverse-KT/";
    private static File folder = new File(DUMMY_PATH_TO_SAVE);

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @Mock
    private BusinessUnitService businessUnitService;

    @Mock
    private ProjectService projectService;

    @Spy
    private MainCommonUtil mainCommonUtil;

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

    @AfterClass
    public static void tearDown(){

        Arrays.stream(folder.listFiles()).filter(t->!t.getName().equals("images")).forEach(t->t.delete());
    }

    @Test
    public void fetchViewProfileDetailsTestShouldThrowException() throws Exception{
        //given
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
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(UserProfile.builder().companyMstr(
                getCompanyRecord()).build());
        when(employeeService.fetchEmployeeForUserProfileSeq(captorInteger.capture())).thenReturn(employeeMstr);
        when(companyService.fetchMapOfCompanyCdAndCompanyName()).thenReturn(getCompanyMapData());

        //then
        RegistrationModelView registrationModelView = userProcessor.fetchViewProfileDetails(12);
        assertThat(captorInteger.getValue(),equalTo(12));
        assertNotNull(registrationModelView.getBusinessUnitDropDown());
        assertNotNull(registrationModelView.getDesignationDropDown());
        assertThat(registrationModelView.getFirstName(),equalTo(employeeMstr.getEmployeeFirstName()));
        assertThat(registrationModelView.getLastName(),equalTo(employeeMstr.getEmployeeLastName()));
    }

    @Test
    public void fetchUserByProfileSeqTestShouldSucceed() throws Exception{
         //given
         UserProfile expected = UserProfile.builder().userId("user_id").userProfileImagePath("image_path").build();

         //when
         when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(expected);

         //then
         UserProfile actual = userProcessor.fetchUserByProfileSeq(10);
         assertNotNull(actual);
         assertThat(captorInteger.getValue(),equalTo(10));
    }


    @Test
    public void saveUserProfileImageTestForNullFileShouldThrowException() throws Exception{
        //given
        UserProfile expected = UserProfile.builder().userId("user_id").userProfileImagePath("image_path").build();

        //then
        expectedException.expect(Exception.class);
        expectedException.expectMessage("File value is null");

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(expected);
        userProcessor.saveUserProfileImage(10,null,"path_to_save");
    }

    @Test
    public void saveUserProfileImageTestForNoUserFileShouldThrowException() throws Exception{

        //then
        expectedException.expect(NullPointerException.class);

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(null);
        userProcessor.saveUserProfileImage(10,null,"path_to_save");
    }

    @Test
    public void saveUserProfileImageTestShouldSucceed() throws Exception{
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("profileImage",new FileInputStream(getDummyFile()));
        UserProfile expected = UserProfile.builder().userId("user_id").build();

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(expected);

        //then
        userProcessor.saveUserProfileImage(12,mockMultipartFile,DUMMY_PATH_TO_SAVE);
        verify(userService,times(1)).createUserProfile(expected,false);
        assertTrue(Arrays.stream(folder.listFiles()).filter(t->!t.getName().equals("images")).count() > 0);
    }

    @Test
    public void updateEmployeeDetailsTestShouldThrowException() throws Exception{
        //then
        expectedException.expect(NullPointerException.class);

        //when
        when(userService.fetchUserByProfileSeq(captorInteger.capture())).thenReturn(null);
        userProcessor.updateEmployeeDetails(10,new RegistrationModelView());
    }

    @Test
    public void updateEmployeeDetailsTestShouldSucceed() throws Exception{
        //given
        RegistrationModelView registrationModelView = generateRegistrationModelAndViewRecord();
        EmployeeMstr employeeMstr = getEmployeeMstrRecord();

        //when
        when(employeeService.fetchEmployeeForUserProfileSeq(captorInteger.capture())).thenReturn(employeeMstr);
        when(businessUnitService.fetchBusinessUnitForBuCd(anyString())).thenReturn(BusinessUnit.builder().build());
        when(projectService.fetchProjectItemForProjectItemSeq(anyInt())).thenReturn(ProjectItem.builder().build());

        //then
        userProcessor.updateEmployeeDetails(12,registrationModelView);
        verify(employeeService,times(1)).createEmployeeRecord(employeeMstr,false);
    }


    private RegistrationModelView generateRegistrationModelAndViewRecord(){
        RegistrationModelView registrationModelView = RegistrationModelView.builder().firstName("first name").lastName("last name").email("email").build();
        registrationModelView.setBusinessUnitSelected("BU_CD");
        registrationModelView.setProjectItemSelected("10");
        return registrationModelView;
    }



    private EmployeeMstr getEmployeeMstrRecord(){
        return EmployeeMstr.builder().employeeFirstName("firstname").employeeLastName("lastname").
                companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).userProfile(UserProfile.builder().build()).build();
    }

    private CompanyMstr getCompanyRecord(){
        CompanyMstr companyMstrOne =  CompanyMstr.builder().companyMstrSeq(1).build();
        DesignationMstr designationMstrOne = DesignationMstr.builder().designationMstrSeq(1).companyMstr(companyMstrOne).designatioName("designation one").priority(0).build();
        DesignationMstr designationMstrTwo = DesignationMstr.builder().designationMstrSeq(2).companyMstr(companyMstrOne).designatioName("designation two").priority(1).build();
        BusinessUnit businessUnitOne = BusinessUnit.builder().businessUnitSeq(1).businessUnitName("BUCOMP ONE").businessCd("BU_ONE").companyMstr(companyMstrOne).build();
        BusinessUnit businessUnitTwo = BusinessUnit.builder().businessUnitSeq(2).businessUnitName("BUCOMP TWO").businessCd("BU_TWO").companyMstr(companyMstrOne).build();
        companyMstrOne.getBusinessUnits().add(businessUnitOne);
        companyMstrOne.getBusinessUnits().add(businessUnitTwo);
        companyMstrOne.getDesignationMstrs().add(designationMstrOne);
        companyMstrOne.getDesignationMstrs().add(designationMstrTwo);
        return companyMstrOne;
    }

    private Map<String,String> getCompanyMapData(){
        return new HashMap<String,String>(){{
            put("TEST_ONE_CD","TEST_COMP");
            put("TEST_TWO_CD","TEST_COMP_TWO");
            put("TEST_THREE_CD","TEST_COMP_THREE");
        }};
    }

    private File getDummyFile(){
        File file = new File(
                getClass().getClassLoader().getResource(DUMMY_SOURCE_IMAGE).getFile()
        );
        return file;
    }
}

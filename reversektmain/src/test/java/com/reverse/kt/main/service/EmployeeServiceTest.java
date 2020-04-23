package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.EmployeeMstrDao;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doNothing;

/**
 * Created by vikas on 22-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeMstrDao employeeMstrDao;

    @InjectMocks
    private EmployeeService employeeService;

    @Captor
    private ArgumentCaptor<EmployeeMstr> captorBean;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createEmployeeRecordShouldSucceed() throws Exception{
        //given
        EmployeeMstr employeeMstr =
                EmployeeMstr.builder().employeeFirstName("firstname").employeeLastName("lastname").
                        companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();

        //when
        doNothing().when(employeeMstrDao).saveOrUpdateEntity(captorBean.capture(),anyBoolean());

        //then
        employeeService.createEmployeeRecord(employeeMstr,true);
        assertThat(employeeMstr.getEmployeeFirstName(),equalTo(captorBean.getValue().getEmployeeFirstName()));

    }
}

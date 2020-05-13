package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by vikas on 22-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts={"classpath:db/master_data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EmployeeMstrDaoTest extends BaseTestDao<EmployeeMstr,EmployeeMstrDao,Integer>{

    @Inject
    private EmployeeMstrDao employeeMstrDao;

    @Override
    protected EmployeeMstr getSingleEntity() {

        return EmployeeMstr.builder().employeeFirstName("firstname").employeeLastName("lastname").
                companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();
    }

    @Test
    @Sql({"classpath:db/employee_data.sql"})
    public void fetchEmployeeMstrForUserProfileTestShouldSucceed() throws Exception{
        //given
        EmployeeMstr expected = getSingleEntity();

        //when
        EmployeeMstr actual = employeeMstrDao.fetchEmployeeMstrForUserProfile(1);
        EmployeeMstr actualNull = employeeMstrDao.fetchEmployeeMstrForUserProfile(23);

        //then
        assertNotNull(actual);
        assertNull(actualNull);
        assertThat(actual.getEmployeeFirstName(), equalTo(expected.getEmployeeFirstName()));
        assertNotNull(actual.getBusinessUnit());
        assertNotNull(actual.getUserProfile());
        assertNotNull(actual.getProjectItem());
        assertNotNull(actual.getDesignationMstr());
    }

    @Override
    protected EmployeeMstrDao getDao() {
        return employeeMstrDao;
    }

    @Override
    @Test
    public void verifySave() {
        assertThat(findById(1).getEmployeeFirstName(),equalTo(getSingleEntity().getEmployeeFirstName()));
    }
}

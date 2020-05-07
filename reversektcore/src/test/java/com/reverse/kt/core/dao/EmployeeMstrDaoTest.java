package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 22-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts={"classpath:db/master_data.sql"})
public class EmployeeMstrDaoTest extends BaseTestDao<EmployeeMstr,EmployeeMstrDao,Integer>{

    @Inject
    private EmployeeMstrDao employeeMstrDao;

    @Override
    protected EmployeeMstr getSingleEntity() {
        return EmployeeMstr.builder().employeeFirstName("FIRST_NAME").employeeLastName("LAST_NAME").
                companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();
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

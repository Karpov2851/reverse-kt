package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 21-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts={"classpath:db/master_data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserRoleDaoTest extends BaseTestDao<UserRole,UserRoleDao,Integer>{

    @Inject
    private UserRoleDao userRoleDao;

    @Test
    public void fetchUserForRoleAndCompanyTestShouldSucceed() throws Exception{
        //when
        UserRole expectedResult = getSingleEntity();

        //when
        UserRole actualResult = userRoleDao.fetchUserForRoleAndCompany(UserRoleIdentifier.ROLE_PROJECT_MANAGER,1);

        //then
        assertNotNull(actualResult);
        assertThat(actualResult.getUserRoleCd(),equalTo(expectedResult.getUserRoleCd()));
    }

    @Override
    protected UserRole getSingleEntity() {
        return UserRole.builder().userRoleCd(UserRoleIdentifier.ROLE_PROJECT_MANAGER.name()).companyMstr(CompanyMstr.builder().companyMstrSeq(1).build()).build();
    }

    @Override
    protected UserRoleDao getDao() {
        return userRoleDao;
    }

    @Override
    public void verifySave() {
        assertThat(findById(1).getUserRoleCd(),equalTo("ROLE_PROJECT_MANAGER"));
    }
}

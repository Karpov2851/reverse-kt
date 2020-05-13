package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.UserProfile;
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
 * Created by vikas on 07-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Sql(scripts={"classpath:db/master_data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserProfileDaoTest extends BaseTestDao<UserProfile,UserProfileDao,Integer>{

    @Inject
    private UserProfileDao userProfileDao;

    @Test
    public void fetchUserProfileByUserIdAndCompanyShouldReturnEntity() throws Exception{
        //given
        UserProfile expected = getSingleEntity();

        //when
        UserProfile actual = userProfileDao.fetchUserProfileByUserIdAndCompany(getSingleEntity().getUserId(),
                getSingleEntity().getCompanyMstr().getCompanyMstrSeq());
        //then
        assertNotNull(actual);
        assertNotNull(actual.getUserRole());
        assertThat(actual.getUserRole().getUserRoleCd(),equalTo("ROLE_PROJECT_MANAGER"));
        assertThat(expected.getUserId(),equalTo(actual.getUserId()));
    }

    @Override
    protected UserProfile getSingleEntity() {
        CompanyMstr c = CompanyMstr.builder().companyMstrSeq(1).build();
        return UserProfile.builder().companyMstr(c)
                .userRole(UserRole.builder().userRoleSeq(1).build()).
                userId("test@company.com").build();
    }

    @Override
    protected UserProfileDao getDao() {
        return userProfileDao;
    }

    @Override
    @Test
    public void verifySave() {
        assertThat(findById(1).getUserId(),equalTo(getSingleEntity().getUserId()));
    }

}

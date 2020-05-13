package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.CompanyMstr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Created by vikas on 04-04-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CompanyDaoTest extends BaseTestDao<CompanyMstr,CompanyDao,Integer> {

    @Inject
    private CompanyDao companyDao;

    @Test
    public void fetchCompanyMstrByCompanyCdShouldReturnRecord() throws Exception {
        //given
        CompanyMstr expected = getSingleEntity();

        //when
        CompanyMstr actual = companyDao.fetchCompanyMstrByCompanyCd("TEST_CD");

        //then
        assertNotNull(actual);
        assertThat(actual.getCompanyCd(), equalTo(expected.getCompanyCd()));
    }

    @Test
    public void fetchCompanyMstrByCompanyCdShouldNotReturnRecord() throws Exception {

        //when
        CompanyMstr actual = companyDao.fetchCompanyMstrByCompanyCd("XYZ");

        //then
        assertNull(actual);
    }

    @Test
    @Sql({"classpath:db/multiple_company.sql"})
    public void fetchAllAcitveCompaniesTestShouldSucceed() throws Exception{

        //when
        List<CompanyMstr> listOfCompanies = companyDao.fetchAllAcitveCompanies();

        //then
        assertThat(listOfCompanies,hasSize(4));

        //when
        CompanyMstr updateCompanyRecord = findById(2);
        updateCompanyRecord.setStatus('F');
        companyDao.saveOrUpdateEntity(updateCompanyRecord,false);
        listOfCompanies = companyDao.fetchAllAcitveCompanies();

        //then
        assertThat(listOfCompanies,hasSize(3));

    }

    @Test
    @Sql({"classpath:db/master_data.sql", "classpath:db/company_related_masters.sql"})
    public void testCompanyRelatedMasterValuesShouldSucceed() throws Exception {
        //when
        CompanyMstr companyRecord = getDao().findById(1);

        //then
        assertNotNull(companyRecord);
        assertThat(companyRecord.getBusinessUnits(), hasSize(3));
        assertThat(companyRecord.getProjects(), hasSize(6));
        assertThat(companyRecord.getSkillMstrs(), hasSize(6));
        assertThat(companyRecord.getDesignationMstrs(), hasSize(4));
    }

    @Override
    protected CompanyMstr getSingleEntity() {
        return CompanyMstr.builder().companyCd("TEST_CD").companyName("Test Company").updatedBy(0).build();
    }

    @Override
    protected CompanyDao getDao() {
        return companyDao;
    }

    @Override
    @Test
    public void verifySave() {
        assertNotNull(findById(1));
    }

}
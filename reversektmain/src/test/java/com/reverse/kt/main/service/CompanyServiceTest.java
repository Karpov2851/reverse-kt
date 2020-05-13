package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.CompanyDao;
import com.reverse.kt.core.model.CompanyMstr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 18-04-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @Mock
    private CompanyDao companyDao;

    @InjectMocks
    private CompanyService companyService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchAllActiveCompaniesTestShouldSucceed() throws Exception{
        //when
        when(companyDao.fetchAllAcitveCompanies()).thenReturn(getCompanies());

        //then
        List<CompanyMstr> companyMstrs = companyService.fetchAllActiveCompanies();
        assertNotNull(companyMstrs);
        assertThat(companyMstrs,hasSize(3));
    }

    @Test
    public void fetchMapOfCompanyCdAndCompanyNameShouldSucceed() throws Exception{

        //when
        when(companyDao.fetchAllAcitveCompanies()).thenReturn(getCompanies());

        //then
        Map<String,String> companyMap = companyService.fetchMapOfCompanyCdAndCompanyName();

        assertThat(companyMap,hasKey("COMP_1"));
        assertThat(companyMap.size(),equalTo(3));
    }

    private List<CompanyMstr> getCompanies(){
        return Arrays.asList(CompanyMstr.builder().companyMstrSeq(1).companyCd("COMP_1").companyName("COMPANY_NAME_1").build(),
                CompanyMstr.builder().companyMstrSeq(2).companyCd("COMP_2").companyName("COMPANY_NAME_2").build(),
                CompanyMstr.builder().companyMstrSeq(3).companyCd("COMP_3").companyName("COMPANY_NAME_3").build());
    }
}

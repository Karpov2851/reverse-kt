package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.BusinessUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by vikas on 12-05-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BusinessUnitDaoTest extends BaseTestDao<BusinessUnit,BusinessUnitDao,Integer>{

    @Inject
    private BusinessUnitDao businessUnitDao;

    @Test
    public void fetchBusinessUnitByBuCdTest() throws Exception{
        //given
        BusinessUnit expected = getSingleEntity();

        //when
        BusinessUnit actual = businessUnitDao.fetchBusinessUnitByBuCd(getSingleEntity().getBusinessCd());

        //then
        assertNotNull(actual);
        assertTrue(actual.getBusinessCd().equals(expected.getBusinessCd()));
    }

    @Override
    protected BusinessUnit getSingleEntity() {
        return BusinessUnit.builder().businessCd("BU_CD").businessUnitName("Business Unit Name").build();
    }

    @Override
    protected BusinessUnitDao getDao() {
        return businessUnitDao;
    }

    @Override
    public void verifySave() {
        assertTrue(findById(1).getBusinessCd().equals(getSingleEntity().getBusinessCd()));
    }
}

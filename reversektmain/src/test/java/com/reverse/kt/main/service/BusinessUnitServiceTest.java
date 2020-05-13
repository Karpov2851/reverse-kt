package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.BusinessUnitDao;
import com.reverse.kt.core.model.BusinessUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 12-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class BusinessUnitServiceTest {

    @Mock
    private BusinessUnitDao businessUnitDao;

    @InjectMocks
    private BusinessUnitService businessUnitService;

    @Captor
    private ArgumentCaptor<String> captorString;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchBusinessUnitForBuCdTestShouldSucceed() throws Exception{
        //given
        BusinessUnit expected = BusinessUnit.builder().businessCd("TEST_CD").businessDesc("Test Description").build();

        //when
        when(businessUnitDao.fetchBusinessUnitByBuCd(captorString.capture())).thenReturn(expected);

        //then
        BusinessUnit actual = businessUnitService.fetchBusinessUnitForBuCd("TEST_CD");
        assertThat(captorString.getValue(),equalTo(expected.getBusinessCd()));
        assertNotNull(actual);
    }
}

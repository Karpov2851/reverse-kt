package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.SkillDao;
import com.reverse.kt.core.model.SkillMstr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by vikas on 11-05-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {

    @Mock
    private SkillDao skillDao;

    @InjectMocks
    private SkillService skillService;

    @Captor
    private ArgumentCaptor<Integer> captorInteger;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchSkillsForProjectItemSeqTest() throws Exception{
        //given
        List<SkillMstr> expected = getSkillMstrRecords();

        //when
        when(skillDao.fetchSkillDetailsForProjectItemSeq(captorInteger.capture())).thenReturn(getSkillMstrRecords());

        //then
        List<SkillMstr> actual = skillService.fetchSkillsForProjectItemSeq(1);
        assertThat(captorInteger.getValue(),equalTo(1));
        assertThat(actual.size(),is(3));
    }

    private List<SkillMstr> getSkillMstrRecords(){
        return Arrays.asList(SkillMstr.builder().skillDesc("Skill one").skillDomain("Skill domain one").build(),
                SkillMstr.builder().skillDesc("Skill two").skillDomain("Skill domain two").build(),
                SkillMstr.builder().skillDesc("Skill three").skillDomain("Skill domain three").build());
    }
}

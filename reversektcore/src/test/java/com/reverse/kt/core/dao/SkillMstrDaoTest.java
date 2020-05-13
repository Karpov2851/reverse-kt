package com.reverse.kt.core.dao;

import com.reverse.kt.core.config.CoreTestConfig;
import com.reverse.kt.core.model.SkillMstr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 11-05-2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SkillMstrDaoTest extends BaseTestDao<SkillMstr,SkillDao,Integer> {


    @Inject
    private SkillDao skillDao;

    @Override
    protected SkillMstr getSingleEntity() {
        return SkillMstr.builder()
                .skillDesc("Skill description").skillDomain("Skill domain").build();
    }

    @Override
    protected SkillDao getDao() {
        return skillDao;
    }

    @Test
    @Sql(scripts={"classpath:db/skillproject_data.sql"})
    public void fetchSkillDetailsForProjectItemSeqTestShouldSucceed() throws Exception{
        //when
        List<SkillMstr> skillMstrs = skillDao.fetchSkillDetailsForProjectItemSeq(1);

        //then
        assertNotNull(skillMstrs);
        assertThat(skillMstrs.size(),is(3));
    }

    @Override
    @Test
    public void verifySave() {
        assertThat(getDao().findById(1).getSkillDesc(), equalTo(getSingleEntity().getSkillDesc()));
    }

}
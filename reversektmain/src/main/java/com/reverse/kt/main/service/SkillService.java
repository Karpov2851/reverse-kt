package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.SkillDao;
import com.reverse.kt.core.model.SkillMstr;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vikas on 11-05-2020.
 */
@Service("skillService")
@Setter(onMethod = @__(@Inject))
public class SkillService {

    private SkillDao skillDao;

    public List<SkillMstr> fetchSkillsForProjectItemSeq(int projectItemSeq) throws Exception{
        return skillDao.fetchSkillDetailsForProjectItemSeq(projectItemSeq);
    }
}

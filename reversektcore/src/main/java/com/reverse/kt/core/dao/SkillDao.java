package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.ProjectItem;
import com.reverse.kt.core.model.ProjectItemSkill;
import com.reverse.kt.core.model.SkillMstr;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikas on 11-05-2020.
 */
@Repository("skillDao")
public class SkillDao extends GenericDao<SkillMstr,Integer>{

    public List<SkillMstr> fetchSkillDetailsForProjectItemSeq(int projectItemSeq) throws Exception{
        Root<SkillMstr> skillMstrRoot = getRoot();
        Join<SkillMstr,ProjectItemSkill> skillProjectItemJoin  = skillMstrRoot.join("projectItemSkills");
        Join<ProjectItemSkill,ProjectItem> projectItemSkillProjectItemJoin = skillProjectItemJoin.join("projectItem");
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(getCriteriaBuilder().equal(projectItemSkillProjectItemJoin.get("projectItemSeq"), projectItemSeq));
        setRoot(skillMstrRoot);
        return findListOfEntireRecordsBasedOnCriteria(conditions.toArray(new Predicate[] {}));
    }
}

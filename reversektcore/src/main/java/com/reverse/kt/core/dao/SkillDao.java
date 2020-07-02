package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.ProjectItem;
import com.reverse.kt.core.model.ProjectItemSkill;
import com.reverse.kt.core.model.SkillMstr;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 11-05-2020.
 */
@Repository("skillDao")
public class SkillDao extends GenericDao<SkillMstr,Integer>{

    public List<SkillMstr> fetchSkillDetailsForProjectItemSeq(int projectItemSeq) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<SkillMstr> skillMstrCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<SkillMstr> skillMstrRoot = skillMstrCriteriaQuery.from(getEntityClass());
        Join<SkillMstr,ProjectItemSkill> skillProjectItemJoin  = skillMstrRoot.join("projectItemSkills");
        Join<ProjectItemSkill,ProjectItem> projectItemSkillProjectItemJoin = skillProjectItemJoin.join("projectItem");
        skillMstrCriteriaQuery.where(criteriaBuilder.equal(projectItemSkillProjectItemJoin.get("projectItemSeq"), projectItemSeq));
        return getEntityManager().createQuery(skillMstrCriteriaQuery).getResultList();
    }
}

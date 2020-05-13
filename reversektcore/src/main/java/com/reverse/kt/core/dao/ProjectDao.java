package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.Project;
import com.reverse.kt.core.specifications.ProjectSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by vikas on 15-04-2020.
 */
@Repository("projectDao")
public class ProjectDao extends GenericDao<Project,Integer>{

    public List<Project> fetchProjectForBusinessUnit(Integer businessUnitSeq) throws Exception{
        Predicate [] businessPredicate = ProjectSpecification.fetchProjectForBusinessUnit(
                getRoot(),getCriteriaBuilder(),businessUnitSeq);
        return findListOfEntireRecordsBasedOnCriteria(businessPredicate);
    }
}

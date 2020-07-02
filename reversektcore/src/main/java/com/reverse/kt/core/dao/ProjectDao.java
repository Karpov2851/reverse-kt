package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.Project;
import com.reverse.kt.core.specifications.ProjectSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 15-04-2020.
 */
@Repository("projectDao")
public class ProjectDao extends GenericDao<Project,Integer>{

    public List<Project> fetchProjectForBusinessUnit(Integer businessUnitSeq) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Project> employeeMstrCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<Project> employeeMstrRoot = employeeMstrCriteriaQuery.from(getEntityClass());
        Predicate [] businessPredicate = ProjectSpecification.fetchProjectForBusinessUnit(
                employeeMstrRoot,criteriaBuilder,businessUnitSeq);
        return getEntityManager().createQuery(employeeMstrCriteriaQuery.where(businessPredicate)).getResultList();
    }
}

package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.specifications.EmployeeMstrSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 22-04-2020.
 */
@Repository("employeeMstr")
public class EmployeeMstrDao extends GenericDao<EmployeeMstr,Integer>{

    public EmployeeMstr fetchEmployeeMstrForUserProfile(int userProfileSeq) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EmployeeMstr> employeeMstrCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<EmployeeMstr> employeeMstrRoot = employeeMstrCriteriaQuery.from(getEntityClass());
        Predicate []  fetchEmployeeMstrForUserProfileSeq = EmployeeMstrSpecification.fetchEmployeeMstrForUserProfileSeq(
                employeeMstrRoot,criteriaBuilder,userProfileSeq);
        List<EmployeeMstr> employeeMstrs = getEntityManager().createQuery(employeeMstrCriteriaQuery.where(fetchEmployeeMstrForUserProfileSeq)).getResultList();
        if(employeeMstrs!=null && employeeMstrs.size()>0){
            return employeeMstrs.get(0);
        }
        return null;
    }
}

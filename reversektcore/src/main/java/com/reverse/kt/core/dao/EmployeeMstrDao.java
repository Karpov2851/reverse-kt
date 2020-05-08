package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.specifications.EmployeeMstrSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by vikas on 22-04-2020.
 */
@Repository("employeeMstr")
public class EmployeeMstrDao extends GenericDao<EmployeeMstr,Integer>{

    public EmployeeMstr fetchEmployeeMstrForUserProfile(int userProfileSeq) throws Exception{
        Predicate []  fetchEmployeeMstrForUserProfileSeq = EmployeeMstrSpecification.fetchEmployeeMstrForUserProfileSeq(
                getRoot(),getCriteriaBuilder(),userProfileSeq);
        List<EmployeeMstr> employeeMstrs = findListOfEntireRecordsBasedOnCriteria(fetchEmployeeMstrForUserProfileSeq);
        if(employeeMstrs!=null && employeeMstrs.size()>0){
            return employeeMstrs.get(0);
        }
        return null;
    }
}

package com.reverse.kt.core.dao;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.core.specifications.UserRoleSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Repository("userRoleDao")
public class UserRoleDao extends GenericDao<UserRole,Integer>{

    public UserRole fetchUserForRoleAndCompany(UserRoleIdentifier uIdentifier, Integer companyMstr) throws Exception{
        UserRole userRole = UserRole.builder().userRoleCd(uIdentifier.name()).companyMstr(CompanyMstr.builder().companyMstrSeq(companyMstr).build()).build();
        Predicate[] predicates = UserRoleSpecification.fetchUserForRoleAndCompany(getRoot(),getCriteriaBuilder(),userRole);
        List<UserRole> userRoles = findListOfEntireRecordsBasedOnCriteria(predicates);
        if(userRoles!=null && userRoles.size()>0){
            return userRoles.get(0);
        }
        return null;
    }
}

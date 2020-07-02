package com.reverse.kt.core.dao;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.core.specifications.UserRoleSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Repository("userRoleDao")
public class UserRoleDao extends GenericDao<UserRole,Integer>{

    public UserRole fetchUserForRoleAndCompany(UserRoleIdentifier uIdentifier, Integer companyMstr) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserRole> userRoleCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<UserRole> userRoleRoot = userRoleCriteriaQuery.from(getEntityClass());

        UserRole userRole = UserRole.builder().userRoleCd(uIdentifier.name()).companyMstr(CompanyMstr.builder().companyMstrSeq(companyMstr).build()).build();
        Predicate[] predicates = UserRoleSpecification.fetchUserForRoleAndCompany(userRoleRoot,criteriaBuilder,userRole);
        List<UserRole> userRoles = getEntityManager().createQuery(userRoleCriteriaQuery.where(predicates)).getResultList();
        if(userRoles!=null && userRoles.size()>0){
            return userRoles.get(0);
        }
        return null;
    }
}

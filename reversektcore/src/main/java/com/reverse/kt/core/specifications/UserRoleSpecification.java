package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.UserRole;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 21-04-2020.
 */
public class UserRoleSpecification {

    final static ISpecification<UserRole> fetchUserForRoleAndCompany = (root, builder, exp) ->{
        Predicate[] pr = (Predicate []) Arrays.asList(builder.equal(root.get("companyMstr"),((UserRole) exp).getCompanyMstr().getCompanyMstrSeq()),
                builder.equal(root.get("userRoleCd"),((UserRole) exp).getUserRoleCd()),
                builder.equal(root.get("status"),'A')).toArray();
        return pr;
    };

    public static Predicate [] fetchUserForRoleAndCompany(Root<UserRole> r, CriteriaBuilder cb, UserRole userProfile){
        return fetchUserForRoleAndCompany.toPredicate(r,cb,userProfile);
    }
}

package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.UserProfile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 07-04-2020.
 */
public class UserProfileSpecification {

    final static ISpecification<UserProfile> fetchUserProfileByCompanyIdAndUser = (root, builder, exp) ->{
        Predicate [] pr = (Predicate [])Arrays.asList(builder.equal(root.get("companyMstr"),((UserProfile) exp).getCompanyMstr().getCompanyMstrSeq()),
                builder.equal(root.get("userId"),((UserProfile) exp).getUserId()),
                builder.equal(root.get("status"),'A')).toArray();
        return pr;
    };

    final static ISpecification<UserProfile> fetchUserProfileByUser = (root, builder, exp) ->{
        Predicate [] pr = (Predicate [])Arrays.asList( builder.equal(root.get("userId"),((UserProfile) exp).getUserId()),
                builder.equal(root.get("status"),'A')).toArray();
        return pr;
    };

    public static Predicate [] fetchUserProfileByCompanyIdAndUser(Root<UserProfile> r, CriteriaBuilder cb, UserProfile userProfile){
        return fetchUserProfileByCompanyIdAndUser.toPredicate(r,cb,userProfile);
    }

    public static Predicate [] fetchUserProfileByUser(Root<UserProfile> r, CriteriaBuilder cb, UserProfile userProfile){
        return fetchUserProfileByUser.toPredicate(r,cb,userProfile);
    }
}

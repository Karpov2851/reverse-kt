package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.specifications.UserProfileSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Repository("userProfileDao")
public class UserProfileDao extends GenericDao<UserProfile,Integer>{

    public UserProfile fetchUserProfileByUserIdAndCompany(String userId,int companyMstrSeq) throws Exception{
       UserProfile userProfile = UserProfile.builder().userId(userId).companyMstr(CompanyMstr.builder().companyMstrSeq(companyMstrSeq).build()).build();
       Predicate [] predicates = UserProfileSpecification.fetchUserProfileByCompanyIdAndUser(getRoot(),getCriteriaBuilder(),userProfile);
       List<UserProfile> userProfiles = findListOfEntireRecordsBasedOnCriteria(predicates);
       if(userProfiles!=null && userProfiles.size()>0){
           return userProfiles.get(0);
       }
       return null;
    }
    public UserProfile fetchUserProfileByUserId(String userId) throws Exception{
        UserProfile userProfile = UserProfile.builder().userId(userId).build();
        Predicate [] predicates = UserProfileSpecification.fetchUserProfileByUser(getRoot(),getCriteriaBuilder(),userProfile);
        List<UserProfile> userProfiles = findListOfEntireRecordsBasedOnCriteria(predicates);
        if(userProfiles!=null && userProfiles.size()>0){
            return userProfiles.get(0);
        }
        return null;
    }
}

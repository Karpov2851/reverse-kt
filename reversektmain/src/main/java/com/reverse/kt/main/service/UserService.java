package com.reverse.kt.main.service;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.dao.UserProfileDao;
import com.reverse.kt.core.dao.UserRoleDao;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.main.security.CustomUser;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by vikas on 16-04-2020.
 */
@Service
@Setter(onMethod = @__(@Inject))
public class UserService implements UserDetailsService{

    private UserProfileDao userProfileDao;

    private UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        try{
            UserProfile userProfile = userProfileDao.fetchUserProfileByUserId(userId);
            if(userProfile == null){
                throw new UsernameNotFoundException("No user found");
            }
            return CustomUser.builder().username(userProfile.getUserId()).password(userProfile.getPassword())
                    .authorities(Arrays.asList(new SimpleGrantedAuthority(userProfile.getUserRole().getUserRoleCd()))).build();
        }catch(Exception e){
           e.printStackTrace();
        }
        return null;
    }

    public void createUserProfile(UserProfile userProfile,boolean isInsert) throws Exception{
        userProfileDao.saveOrUpdateEntity(userProfile,isInsert);
    }

    public UserRole fetchUserForRoleAndCompany(UserRoleIdentifier userRoleIdentifier, Integer companyMstr) throws Exception{
        return userRoleDao.fetchUserForRoleAndCompany(userRoleIdentifier,companyMstr);
    }

    public UserProfile fetchUserProfileByUserId(String userName) throws Exception{
        return userProfileDao.fetchUserProfileByUserId(userName);
    }
}

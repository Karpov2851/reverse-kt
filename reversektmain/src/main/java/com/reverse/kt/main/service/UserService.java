package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.UserProfileDao;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.main.security.CustomUser;
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

public class UserService implements UserDetailsService{

    private UserProfileDao userProfileDao;

    @Inject
    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

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
}

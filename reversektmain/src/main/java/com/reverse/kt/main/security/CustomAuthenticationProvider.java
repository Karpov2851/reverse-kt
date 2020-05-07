package com.reverse.kt.main.security;

import com.reverse.kt.main.service.UserService;
import com.reverse.kt.main.util.MainCommonUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by vikas on 16-04-2020.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private UserService userService;

    private MainCommonUtil mainCommonUtil;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Inject
    public void setMainCommonUtil(MainCommonUtil mainCommonUtil) {
        this.mainCommonUtil = mainCommonUtil;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UsernamePasswordAuthenticationToken token = null;

        UserDetails userDetails = userService.loadUserByUsername(username);
        if(userDetails!=null && mainCommonUtil.getPasswordEncoder().matches(password,userDetails.getPassword()) ){
            token = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(),userDetails.getAuthorities());
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

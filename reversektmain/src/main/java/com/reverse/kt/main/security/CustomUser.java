package com.reverse.kt.main.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by vikas on 16-04-2020.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUser implements UserDetails{

    private String password;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    private int userProfileSeq;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserProfileSeq() {
        return userProfileSeq;
    }

    public void setUserProfileSeq(int userProfileSeq) {
        this.userProfileSeq = userProfileSeq;
    }
}

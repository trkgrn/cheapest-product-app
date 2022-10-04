package com.trkgrn_theomer.cheapspring.api.service.userdetail;

import com.trkgrn_theomer.cheapspring.api.model.concretes.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails() {

    }

    public CustomUserDetails(User user){
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    public String getRole(){
        return user.getRole();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    public Long getId(){return  user.getUserId();}

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "user=" + user +
                '}';
    }
}

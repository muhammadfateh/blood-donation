package com.practice.demo.blooddonation.oauth;

import com.practice.demo.blooddonation.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails implements UserDetails {
    private final User user;
    private final List<UserAuthGroup> userAuthGroupList;

    public CustomUserDetails(User user, List<UserAuthGroup> userGroups){
        super();
        this.user=user;
        this.userAuthGroupList =userGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null == userAuthGroupList){
            return Collections.emptySet();
        }

        Set<GrantedAuthority> grantedAuthoritySet= new HashSet<>();
        userAuthGroupList.forEach(a->{
            grantedAuthoritySet.add(new SimpleGrantedAuthority(a.getAuthGroup()));
        });

        return grantedAuthoritySet;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

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
}

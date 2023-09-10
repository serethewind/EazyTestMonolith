package com.eazytest.eazytest.config;


import com.eazytest.eazytest.entity.userType.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String username;
    private String password;
    private Set<GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(UserType user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.grantedAuthorities = user.getRoleEnums().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String email(){
        return email;
    }

    @Override
    public String getUsername() {
        return username;
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

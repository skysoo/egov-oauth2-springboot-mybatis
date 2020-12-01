package com.oauth2.authenticationserver.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@Getter
@EqualsAndHashCode
public class UserVo implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;
    private String password;
    private String name;
    private String provider;
    private String roles;

    @Builder
    public UserVo(String uid, String password, String name, String provider, String roles) {
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.uid;
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

    public <R> String orElseThrow(Function<? super Exception, ? extends R> mapper) {
        return null;
    }
}

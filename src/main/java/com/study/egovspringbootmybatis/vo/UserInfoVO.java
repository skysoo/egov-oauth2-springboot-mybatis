package com.study.egovspringbootmybatis.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@EqualsAndHashCode
public class UserInfoVO implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;
    private String password;
    private String name;
    private String provider;
    private String roles;

    @Builder
    public UserInfoVO(String uid, String password, String name, String provider, String roles) {
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

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}

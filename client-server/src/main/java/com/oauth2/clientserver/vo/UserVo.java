package com.oauth2.clientserver.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class UserVo implements Serializable {
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
}

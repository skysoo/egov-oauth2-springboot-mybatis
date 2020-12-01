package com.oauth2.clientserver.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public class OAuthToken {
    private static final long serialVersionUID = 1L;

    @SerializedName(value = "ACESS_TOKEN", alternate = "access_token")
    private final String accessToken;
    @SerializedName(value = "TOKEN_TYPE", alternate = "token_type")
    private final String tokenType;
    @SerializedName(value = "REFRESH_TOKEN", alternate = "refresh_token")
    private final String refreshToken;
    @SerializedName(value = "EXPIRES_IN", alternate = "expires_in")
    private final long expiresIn;
    @SerializedName(value = "SCOPE", alternate = "scope")
    private final String scope;
    @SerializedName(value = "JTI", alternate = "jti")
    private final String jti;

    @Builder
    public OAuthToken(String accessToken, String tokenType, String refreshToken, long expiresIn, String scope, String jti) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.jti = jti;
    }
}

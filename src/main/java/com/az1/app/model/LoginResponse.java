package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private Integer timeExpiration;
    private String refreshToken;
    private Integer refreshTimeExpiration;
    private String userName;
    private Collection<String> scopes;
    private Collection<String> authorizes;
}

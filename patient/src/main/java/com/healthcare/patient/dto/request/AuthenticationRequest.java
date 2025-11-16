package com.healthcare.patient.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

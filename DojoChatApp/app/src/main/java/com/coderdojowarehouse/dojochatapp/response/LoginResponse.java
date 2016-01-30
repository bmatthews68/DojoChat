package com.coderdojowarehouse.dojochatapp.response;

import com.btmatthews.rest.core.client.AbstractResponse;

public final class LoginResponse extends AbstractResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}

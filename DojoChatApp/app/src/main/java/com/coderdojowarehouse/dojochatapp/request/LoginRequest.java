package com.coderdojowarehouse.dojochatapp.request;

import com.btmatthews.rest.core.client.Request;

public final class LoginRequest implements Request {

    private String username;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(final String username,
                        final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}

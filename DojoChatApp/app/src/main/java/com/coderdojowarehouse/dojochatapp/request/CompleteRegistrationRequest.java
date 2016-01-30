package com.coderdojowarehouse.dojochatapp.request;

import com.btmatthews.rest.core.client.Request;

public final class CompleteRegistrationRequest implements Request {

    private String password;

    public CompleteRegistrationRequest() {
    }

    public CompleteRegistrationRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}

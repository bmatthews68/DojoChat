package com.coderdojowarehouse.dojochatapp.response;

import com.btmatthews.rest.core.client.AbstractResponse;

public final class BeginRegistrationResponse extends AbstractResponse {

    private String nickname;

    private String token;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setId(final String token) {
        this.token = token;
    }
}

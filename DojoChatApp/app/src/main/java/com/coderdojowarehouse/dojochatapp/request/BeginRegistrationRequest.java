package com.coderdojowarehouse.dojochatapp.request;

import com.btmatthews.rest.core.client.Request;

public final class BeginRegistrationRequest implements Request {

    private String fullName;

    private String nickname;

    private String emailAddress;

    public BeginRegistrationRequest() {
    }

    public BeginRegistrationRequest(String fullName, String nickname, String emailAddress) {
        this.fullName = fullName;
        this.nickname = nickname;
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

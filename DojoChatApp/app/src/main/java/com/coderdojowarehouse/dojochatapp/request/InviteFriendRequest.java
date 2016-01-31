package com.coderdojowarehouse.dojochatapp.request;

import com.btmatthews.rest.core.client.Request;

public final class InviteFriendRequest implements Request {

    private String to;

    private String message;

    public InviteFriendRequest() {

    }

    public InviteFriendRequest(final String to, final String message) {
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return to;
    }

    public void setFrom(final String from) {
        this.to = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

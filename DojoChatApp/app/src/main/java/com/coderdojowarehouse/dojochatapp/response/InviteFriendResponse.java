package com.coderdojowarehouse.dojochatapp.response;

import com.btmatthews.rest.core.client.AbstractResponse;

public class InviteFriendResponse extends AbstractResponse {

    private String invite;

    public String getInvite() {
        return invite;
    }

    public void setInvite(final String invite) {
        this.invite = invite;
    }
}

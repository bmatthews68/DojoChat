package com.coderdojowarehouse.dojochatapp.response;

import com.coderdojowarehouse.dojochatapp.model.Invite;

import java.util.List;

public class InvitesResponse {

    private List<Invite> invites;

    public List<Invite> getInvites() {
        return invites;
    }

    public void setInvites(final List<Invite> invites) {
        this.invites = invites;
    }
}

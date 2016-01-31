package com.coderdojowarehouse.dojochatapp.model;

public final class Invite {

    private String invite;

    private User fromUser;

    private User toUser;

    private String message;

    public String getInvite() {
        return invite;
    }

    public void setInvite(final String invite) {
        this.invite = invite;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(final User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(final User toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

package com.coderdojowarehouse.dojochatapp.response;

import com.btmatthews.rest.core.client.AbstractResponse;
import com.coderdojowarehouse.dojochatapp.model.User;

import java.util.List;

public class UsersResponse extends AbstractResponse {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }
}

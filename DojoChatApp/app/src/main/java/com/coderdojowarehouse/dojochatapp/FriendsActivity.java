package com.coderdojowarehouse.dojochatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.coderdojowarehouse.dojochatapp.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public final class FriendsActivity extends AppCompatActivity {

    @Bind(R.id.friendList)
    ListView friendList;

    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ButterKnife.bind(this);

        friendsAdapter = new FriendsAdapter();
        friendList.setAdapter(friendsAdapter);
    }

    class FriendsAdapter extends ArrayAdapter<User> {
        public FriendsAdapter() {
            super(FriendsActivity.this, R.layout.activity_friends);
        }
    }
}

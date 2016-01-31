package com.coderdojowarehouse.dojochatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Check if we have a session

        // Forward to the Login activity when we don't have a valid session

        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

package com.coderdojowarehouse.dojochatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.coderdojowarehouse.dojochatapp.api.ChatClient;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class BeginRegistrationActivity extends AppCompatActivity {

    private static final String TAG = "BeginRegistration";

    @Bind(R.id.fullNameText)
    EditText fullNameText;

    @Bind(R.id.nicknameText)
    EditText nicknameText;

    @Bind(R.id.emailAddressText)
    EditText emailAddressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_registration);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.beginButton)
    protected void onBeginClicked() {
        ChatClient.getInstance()
                .beginRegistration(
                        fullNameText.getText().toString(),
                        nicknameText.getText().toString(),
                        emailAddressText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeginRegistrationResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e(TAG, "Registration request failed", e);
                        Toast.makeText(BeginRegistrationActivity.this, R.string.registration_failed_err, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(final BeginRegistrationResponse response) {
                        if (response.isOk()) {
                            // TODO Display some kind of confirmation screen
                        } else {
                            // TODO Report error during registration with toast
                        }
                    }
                });
    }

    @OnClick(R.id.cancelButton)
    protected void onCancelClicked() {
        finish();
    }
}

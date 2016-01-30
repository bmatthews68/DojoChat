package com.coderdojowarehouse.dojochatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.btmatthews.rest.core.client.Response;
import com.coderdojowarehouse.dojochatapp.api.ChatClient;
import com.coderdojowarehouse.dojochatapp.response.CompleteRegistrationResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompleteRegistrationActivity extends AppCompatActivity {

    private static final String TAG = "CompleteRegistration";

    private String token;

    @Bind(R.id.passwordEdit)
    EditText passwordText;

    @Bind(R.id.confirmPasswordEdit)
    EditText confirmPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.finishButton)
    protected void onFinishClicked() {
        ChatClient.getInstance()
                .completeRegistration(
                        token,
                        passwordText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompleteRegistrationResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e(TAG, "Registration completion failed", e);
                        Toast.makeText(CompleteRegistrationActivity.this, R.string.registration_failed_err, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(final CompleteRegistrationResponse response) {
                        if (response.isOk()) {
                            // TODO Should we do a notification
                            final Intent intent = new Intent(CompleteRegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(CompleteRegistrationActivity.this, R.string.invalid_registration_token_err, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @OnClick(R.id.cancelButton)
    protected void onCancelClicked() {
        finish();
    }
}

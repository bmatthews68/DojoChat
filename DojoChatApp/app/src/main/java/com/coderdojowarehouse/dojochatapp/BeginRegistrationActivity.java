package com.coderdojowarehouse.dojochatapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coderdojowarehouse.dojochatapp.api.ChatClient;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class BeginRegistrationActivity extends AbstractValidatedActivity {

    private static final String TAG = "BeginRegistration";

    @NotEmpty
    @Bind(R.id.fullNameText)
    EditText fullNameText;

    @NotEmpty
    @Bind(R.id.nicknameText)
    EditText nicknameText;

    @NotEmpty
    @Email
    @Bind(R.id.emailAddressText)
    EditText emailAddressText;

    @Bind(R.id.beginButton)
    Button beginButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_registration);

        setupValidation();
        addValidation(fullNameText);
        addValidation(nicknameText);
        addValidation(emailAddressText);
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

    @Override
    public void onValidationSucceeded() {
        beginButton.setEnabled(true);
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors) {
        beginButton.setEnabled(false);
    }
}

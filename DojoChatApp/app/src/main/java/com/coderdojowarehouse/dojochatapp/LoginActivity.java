package com.coderdojowarehouse.dojochatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coderdojowarehouse.dojochatapp.api.ChatClient;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class LoginActivity extends AbstractValidatedActivity {

    private static final String TAG = "Login";

    /**
     * This object is bound to the Username input field by ButterKnife.
     */
    @NotEmpty
    @Bind(R.id.usernameEdit)
    EditText usernameText;

    /**
     * This object is bound to the Password input field by ButterKnife.
     */
    @NotEmpty
    @Bind(R.id.passwordEdit)
    EditText passwordText;

    @Bind(R.id.loginButton)
    Button loginButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupValidation();
        addValidation(usernameText);
        addValidation(passwordText);

        loginButton.setEnabled(false);
    }

    /**
     * This method is invoked when the Login button is pressed. It is bound to the button
     * object using ButterKnife.
     * <p>
     * When the user clicks the Login button they the login process is initiated.
     */
    @OnClick(R.id.loginButton)
    protected void onLoginClicked() {
        ChatClient.getInstance()
                .login(usernameText.getText().toString(), passwordText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 401) {
                                Toast.makeText(LoginActivity.this, R.string.invalid_credentials_err, Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        Log.e(TAG, "Login request failed", e);
                        Toast.makeText(LoginActivity.this, R.string.login_failed_err, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(final LoginResponse response) {
                        if (response.isOk()) {
                            // TODO Persist the login token
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.invalid_credentials_err, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * This method is invoked when the Register button is pressed. It is bound to the button
     * object using ButterKnife.
     * <p>
     * When the user clicks the Register button they are redirected to the Begin Registration
     * activity where they will supply the details necessary to register a new account.
     */
    @OnClick(R.id.registerButton)
    protected void onRegisterClicked() {
        final Intent intent = new Intent(this, BeginRegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        loginButton.setEnabled(true);
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors) {
        loginButton.setEnabled(false);
    }
}

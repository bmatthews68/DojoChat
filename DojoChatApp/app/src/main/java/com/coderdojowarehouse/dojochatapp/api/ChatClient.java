package com.coderdojowarehouse.dojochatapp.api;

import com.coderdojowarehouse.dojochatapp.response.LogoutResponse;
import com.coderdojowarehouse.dojochatapp.request.LoginRequest;
import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.CompleteRegistrationResponse;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public final class ChatClient {

    private final RegistrationService registrationService;

    private final LoginService loginService;

    private static volatile ChatClient instance;

    public static final ChatClient getInstance() {
        if (instance == null) {
            synchronized (ChatClient.class) {
                if (instance == null) {
                    instance = new ChatClient();
                }
            }
        }
        return instance;
    }
    protected ChatClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.coderdojowarehouse.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registrationService = retrofit.create(RegistrationService.class);
        loginService = retrofit.create(LoginService.class);
    }


    public void beginRegistration(final BeginRegistrationRequest request,
                                  final Callback<BeginRegistrationResponse> callback) {
        registrationService.begin(request).enqueue(callback);
    }

    public void completeRegistration(final String token,
                                     final CompleteRegistrationRequest request,
                                     final Callback<CompleteRegistrationResponse> callback) {
        registrationService.complete(token, request).enqueue(callback);
    }

    public void login(final LoginRequest request,
                      final Callback<LoginResponse> callback) {
        loginService.login(request).enqueue(callback);
    }

    public void logout(final String token,
                       final Callback<LogoutResponse> callback) {
        loginService.logout(token).enqueue(callback);
    }
}

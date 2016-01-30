package com.coderdojowarehouse.dojochatapp.api;

import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.LoginRequest;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.CompleteRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;
import com.coderdojowarehouse.dojochatapp.response.LogoutResponse;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;

public final class ChatClient {

    private static volatile ChatClient instance;
    private final RegistrationService registrationService;
    private final LoginService loginService;

    protected ChatClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.coderdojowarehouse.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registrationService = retrofit.create(RegistrationService.class);
        loginService = retrofit.create(LoginService.class);
    }

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

    public Observable<BeginRegistrationResponse> beginRegistration(final String fullName,
                                                                   final String nickname,
                                                                   final String emailAddress) {
        return beginRegistration(new BeginRegistrationRequest(fullName, nickname, emailAddress));
    }

    public Observable<BeginRegistrationResponse> beginRegistration(final BeginRegistrationRequest request) {
        return registrationService.begin(request);
    }

    public Observable<CompleteRegistrationResponse> completeRegistration(final String token,
                                                                         final String password) {
        return completeRegistration(token, new CompleteRegistrationRequest(password));
    }

    public Observable<CompleteRegistrationResponse> completeRegistration(final String token,
                                                                         final CompleteRegistrationRequest request) {
        return registrationService.complete(token, request);
    }

    public Observable<LoginResponse> login(final String username,
                                           final String password) {
        return login(new LoginRequest(username, password));
    }

    public Observable<LoginResponse> login(final LoginRequest request) {
        return loginService.login(request);
    }

    public Observable<LogoutResponse> logout(final String token) {
        return loginService.logout(token);
    }
}

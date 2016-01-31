package com.coderdojowarehouse.dojochatapp.api;

import com.btmatthews.rest.core.client.SimpleResponse;
import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.LoginRequest;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;
import com.coderdojowarehouse.dojochatapp.response.UsersResponse;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;

public final class ChatClient {

    private static volatile ChatClient instance;
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final FriendService friendService;

    /**
     * The constructor is private because ChatClient is a singleton and the only way get the
     * ChatClient object is by calling {@link #getInstance}.
     * <p/>
     * The constructor is responsible for configuring <a href="http://square.github.io/retrofit/">Retrofit</a>
     * and creating objects services from interfaces.
     */
    private ChatClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.coderdojowarehouse.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // Return RxJava observables
                .addConverterFactory(GsonConverterFactory.create()) // Marshal POJOs using Gson
                .build();
        registrationService = retrofit.create(RegistrationService.class);
        loginService = retrofit.create(LoginService.class);
        friendService = retrofit.create(FriendService.class);
    }

    /**
     * This factory method
     *
     * @return
     */
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

    /**
     * @param fullName
     * @param nickname
     * @param emailAddress
     * @return
     */
    public Observable<BeginRegistrationResponse> beginRegistration(final String fullName,
                                                                   final String nickname,
                                                                   final String emailAddress) {
        return beginRegistration(new BeginRegistrationRequest(fullName, nickname, emailAddress));
    }

    /**
     * @param request
     * @return
     */
    public Observable<BeginRegistrationResponse> beginRegistration(final BeginRegistrationRequest request) {
        return registrationService.begin(request);
    }

    /**
     * @param token
     * @param password
     * @return
     */
    public Observable<SimpleResponse> completeRegistration(final String token,
                                                           final String password) {
        return completeRegistration(token, new CompleteRegistrationRequest(password));
    }

    /**
     * @param token
     * @param request
     * @return
     */
    public Observable<SimpleResponse> completeRegistration(final String token,
                                                           final CompleteRegistrationRequest request) {
        return registrationService.complete(token, request);
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public Observable<LoginResponse> login(final String username,
                                           final String password) {
        return login(new LoginRequest(username, password));
    }

    /**
     * @param request
     * @return
     */
    public Observable<LoginResponse> login(final LoginRequest request) {
        return loginService.login(request);
    }

    /**
     * @param token
     * @return
     */
    public Observable<SimpleResponse> logout(final String token) {
        return loginService.logout(token, token);
    }

    public Observable<UsersResponse> friends(final String token) {
        return friendService.friends(token);
    }
}

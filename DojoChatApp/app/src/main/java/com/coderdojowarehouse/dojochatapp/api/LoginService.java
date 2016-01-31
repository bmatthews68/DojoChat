package com.coderdojowarehouse.dojochatapp.api;

import com.btmatthews.rest.core.client.SimpleResponse;
import com.coderdojowarehouse.dojochatapp.request.LoginRequest;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface LoginService {

    @POST("/api/sessions")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @DELETE("/api/sessions/{token}")
    Observable<SimpleResponse> logout(@Header("Authorization") String authToken, @Path("token") String token);
}

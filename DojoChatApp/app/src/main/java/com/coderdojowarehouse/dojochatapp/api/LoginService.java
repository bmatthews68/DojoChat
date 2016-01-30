package com.coderdojowarehouse.dojochatapp.api;

import com.coderdojowarehouse.dojochatapp.response.LogoutResponse;
import com.coderdojowarehouse.dojochatapp.request.LoginRequest;
import com.coderdojowarehouse.dojochatapp.response.LoginResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;
import retrofit.http.Path;

public interface LoginService {

    @POST("/api/sessions")
    Call<LoginResponse> login(@Body LoginRequest request);

    @DELETE("/api/sessions/{token}")
    Call<LogoutResponse> logout(@Path("token") String token);
}

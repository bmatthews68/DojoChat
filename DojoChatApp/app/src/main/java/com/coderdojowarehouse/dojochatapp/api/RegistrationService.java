package com.coderdojowarehouse.dojochatapp.api;

import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.CompleteRegistrationResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface RegistrationService {

    @POST("/api/registrations")
    Call<BeginRegistrationResponse> begin(@Body BeginRegistrationRequest request);

    @PUT("/api/registration/{token}")
    Call<CompleteRegistrationResponse> complete(@Path("token") String token, @Body CompleteRegistrationRequest request);
}

package com.coderdojowarehouse.dojochatapp.api;

import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;
import com.coderdojowarehouse.dojochatapp.response.CompleteRegistrationResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RegistrationService {

    @POST("/api/registrations")
    Observable<BeginRegistrationResponse> begin(@Body BeginRegistrationRequest request);

    @PUT("/api/registration/{token}")
    Observable<CompleteRegistrationResponse> complete(@Path("token") String token, @Body CompleteRegistrationRequest request);
}

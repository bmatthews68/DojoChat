package com.coderdojowarehouse.dojochatapp.api;

import com.btmatthews.rest.core.client.SimpleResponse;
import com.coderdojowarehouse.dojochatapp.request.BeginRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.request.CompleteRegistrationRequest;
import com.coderdojowarehouse.dojochatapp.response.BeginRegistrationResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RegistrationService {

    @POST("/api/registrations")
    Observable<BeginRegistrationResponse> begin(@Body BeginRegistrationRequest request);

    @PUT("/api/registration/{token}")
    Observable<SimpleResponse> complete(@Path("token") String token, @Body CompleteRegistrationRequest request);
}

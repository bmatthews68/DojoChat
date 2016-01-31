package com.coderdojowarehouse.dojochatapp.api;

import com.btmatthews.rest.core.client.SimpleResponse;
import com.coderdojowarehouse.dojochatapp.request.InviteFriendRequest;
import com.coderdojowarehouse.dojochatapp.response.InviteFriendResponse;
import com.coderdojowarehouse.dojochatapp.response.InvitesResponse;
import com.coderdojowarehouse.dojochatapp.response.UsersResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface FriendService {

    @GET("/api/friends")
    Observable<UsersResponse> friends(@Header("Authorization") String authToken);

    @DELETE("/api/friends/{friend}")
    Observable<SimpleResponse> unfriend(@Header("Authorization") String authToken, @Path("friend") String friend);

    @GET("/api/invites")
    Observable<InvitesResponse> invites(@Header("Authorization") String authToken);

    @POST("/api/invites")
    Observable<InviteFriendResponse> invite(@Header("Authorization") String authToken, @Body InviteFriendRequest request);

    @PUT("/api/invites/{invite}?action=accept")
    Observable<SimpleResponse> accept(@Header("Authorization") String authToken, @Path("invite") String invite);

    @PUT("/api/invites/{invite}?action=decline")
    Observable<SimpleResponse> decline(@Header("Authorization") String authToken, @Path("invite") String invite);

    @DELETE("/api/invites/{invite}")
    Observable<SimpleResponse> withdraw(@Header("Authorization") String authToken, @Path("decline") String invite);
}

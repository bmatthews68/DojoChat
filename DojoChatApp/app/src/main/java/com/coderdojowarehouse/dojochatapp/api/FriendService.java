package com.coderdojowarehouse.dojochatapp.api;

import com.btmatthews.rest.core.client.SimpleResponse;
import com.coderdojowarehouse.dojochatapp.request.InviteFriendRequest;
import com.coderdojowarehouse.dojochatapp.response.InviteFriendResponse;
import com.coderdojowarehouse.dojochatapp.response.InvitesResponse;
import com.coderdojowarehouse.dojochatapp.response.UsersResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface FriendService {

    @GET("/api/friends")
    Observable<UsersResponse> friends();

    @DELETE("/api/friends/{friend}")
    Observable<SimpleResponse> unfriend();

    @GET("/api/invites")
    Observable<InvitesResponse> invites();

    @POST("/api/invites")
    Observable<InviteFriendResponse> invite(@Body InviteFriendRequest request);

    @PUT("/api/invites/{invite}?action=accept")
    Observable<SimpleResponse> accept(@Path("invite") String invite);

    @PUT("/api/invites/{invite}?action=decline")
    Observable<SimpleResponse> decline(@Path("invite") String invite);

    @DELETE("/api/invites/{invite}")
    Observable<SimpleResponse> withdraw(@Path("decline") String invite);
}

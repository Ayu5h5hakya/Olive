package com.example.styledmap;

import com.example.styledmap.Models.AuthenticationResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ayush on 1/18/17.
 */

public interface RMAPinterface {

    String BASE_URL = "http://rosia.udn.com.np:8092/";

    @GET("api/user/authenticate")
    Observable<AuthenticationResponse> getAuthenticationToken();

}

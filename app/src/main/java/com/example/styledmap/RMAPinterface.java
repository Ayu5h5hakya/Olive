package com.example.styledmap;

import com.example.styledmap.Models.AuthenticationResponse;
import com.example.styledmap.Models.CategoryListingResponse;
import com.example.styledmap.Models.ClassificationListingResponse;
import com.example.styledmap.Models.OutletDetailResponse;
import com.example.styledmap.Models.SBDListingResponse;
import com.example.styledmap.Models.SKUListingResponse;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ayush on 1/18/17.
 */

public interface RMAPinterface {

    String BASE_URL = "http://rosia.udn.com.np:8092/";

    @FormUrlEncoded
    @POST("api/user/authenticate")
    Observable<AuthenticationResponse> getAuthenticationToken(@Field("username") String username,
                                                              @Field("password") String password);

    @GET("api/category/list?")
    Observable<CategoryListingResponse> getCategoryListing(@Query("token") String token);

    @GET("api/sbd/classified/catalogs?")
    Observable<SBDListingResponse> getSBDlisting(@Query("token") String token);

    @GET("api/catalogs?")
    Observable<SKUListingResponse> getSKUListing(@Query("token") String token);

    @GET("api/ro/category/catalog?")
    Observable<ClassificationListingResponse> getClassificationListing(@Query("token") String token);

    @GET("api/street/list?")
    Observable<OutletDetailResponse> getstreetoutletlisting(@Query("token") String token,
                                                            @Query("data") String data);

}

package com.example.tourism_portal;

import com.example.tourism_portal.models.ServerRequest;
import com.example.tourism_portal.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("TourismTripPortal/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}

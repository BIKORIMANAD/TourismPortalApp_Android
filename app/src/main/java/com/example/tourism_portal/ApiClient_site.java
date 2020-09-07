package com.example.tourism_portal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 18-09-2019 End. MadeCode.
 */

class ApiClient_site {

    private static final String BASE_URL = "http://172.20.10.6/2019/TourismTripPortal/";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}

package com.example.tourism_portal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 18-09-2019 End. MadeCode.
 */
public interface ApiInterface_site {

    @POST("get_site.php")
    Call<List<Site>> getPets();

    @FormUrlEncoded
    @POST("add_site.php")
    Call<Site> insertPet(
            @Field("key") String key,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_site.php")
    Call<Site> updatePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_site.php")
    Call<Site> deletePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_love_site.php")
    Call<Site> updateLove(
            @Field("key") String key,
            @Field("id") int id,
            @Field("love") boolean love);

}

package com.hassan.a.abubakr.pojecttemplate;

import com.hassan.a.abubakr.pojecttemplate.models.Announcement;
import com.hassan.a.abubakr.pojecttemplate.models.Duty;
import com.hassan.a.abubakr.pojecttemplate.models.Result;
import com.hassan.a.abubakr.pojecttemplate.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * we used Retrofit to handle background calls for it's great efficiency in managing threads and
 * for it's great abstraction of the json-object conversion
 */
public class API {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    /**
     * each method is self explanatory
     */

    public interface APIService{
        @POST("/api/login")
        @FormUrlEncoded
        Call<User> login(
                @Field("userId") String id,
                @Field("password") String password
        );

        @POST("/api/logout")
        @FormUrlEncoded
        Call<Object> logout(
                @Field("userId") int id,
                @Header("Authorization") String token
        );


        @POST("/api/user/updatePassword")
        @FormUrlEncoded
        Call<Object> updatePassword(
                @Field("new_password") String newPassword,
                @Field("userId") String id,
                @Header("Authorization") String token
        );

        @POST("/api/user/getAnnouncements")
        @FormUrlEncoded
        Call<List<Announcement>> getAnnouncements(
                @Field("userId") int id,
                @Header("Authorization") String token
        );

        @POST("/api/user/getPersonalAnnouncements")
        @FormUrlEncoded
        Call<List<Announcement>> getPersonalAnnouncements(
                @Field("userId") int id,
                @Header("Authorization") String token
        );

        @POST("/api/user/getResults")
        @FormUrlEncoded
        Call<List<Result>> getResults(
                @Field("userId") int id,
                @Header("Authorization") String token
        );

        @POST("/api/user/getDuty")
        @FormUrlEncoded
         Call<Duty> getDuty(
                @Field("userId") int id,
                @Header("Authorization") String token
        );


        @POST("/api/user/reportMessage")
        @FormUrlEncoded
        Call<Object> reportMessage(
                @Field("title") String title,
                @Field("body") String body,
                @Field("userId") int id,
                @Header("Authorization") String token
        );
    }
}

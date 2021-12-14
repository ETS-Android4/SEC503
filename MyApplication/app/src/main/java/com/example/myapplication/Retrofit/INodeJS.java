package com.example.myapplication.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);
    @POST("logout")
    @FormUrlEncoded
    Observable<String> logoutUser(@Field("session_id") String session_id);

    @POST("otp")
    @FormUrlEncoded
    Observable<String> otpuser(@Field("otp") String otp,
                               @Field("session_id") String session_id);



}

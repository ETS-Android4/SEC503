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

    @POST("KeyManagemnt")
    @FormUrlEncoded
    Observable<String> KeyManagemnt(@Field("option") String option,
                                    @Field("Pubkey") String Pubkey,
                                    @Field("session_id") String session_id,
                                    @Field("peer_id") String peer_id);


    @POST("ChattManagement")
    @FormUrlEncoded
    Observable<String> ChatManagemnt(@Field("option") String option,
                                     @Field("request_id") String request_id,
                                     @Field("requester_id") String requester_id,
                                     @Field("email") String email,
                                     @Field("responded_status") String responded_status,
                                     @Field("chatting_id")  String chatting_id);
    @POST("/ShareKey/")
    @FormUrlEncoded
    Observable<String> ShareKey(@Field("chatting_id") String chatting_id,
                                @Field("encrypted_key") String encrypted_key);

    @POST("/AskSecKey/")
    @FormUrlEncoded
    Observable<String> AskSecKey(@Field("request_id") String request_id);


//    var option = post_data.option;
//    var Pubkey = post_data.Pubkey;
//    var session_id = post_data.session_id;
//    var peer_id = post_data.peer_id;



}

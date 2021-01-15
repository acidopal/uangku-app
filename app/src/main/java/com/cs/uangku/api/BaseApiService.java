package com.cs.uangku.api;

import com.cs.uangku.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> register(@Field("name") String name,
                                @Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("transaction")
    Call<ResponseBody> saveTransaction(@Field("amount") int amount,
                                @Field("category") String category,
                                @Field("description") String password);
}

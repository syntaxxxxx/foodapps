package com.fiqri.gofoodsederhana.network;

import com.fiqri.gofoodsederhana.model.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<ResponseRegister> register(
            @Field("vsnama") String name,
            @Field("vsalamat") String alamat,
            @Field("vsjenkel") String jenkel,
            @Field("vsnotelp") String noTlp,
            @Field("vsusername") String userName,
            @Field("vslevel") String level,
            @Field("vspassword") String password);

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<ResponseRegister> login(
            @Field("edtusername") String userName,
            @Field("edtpassword") String password,
            @Field("vslevel") String level);
}

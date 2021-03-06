package com.fiqri.gofoodsederhana.network;

import com.fiqri.gofoodsederhana.model.ResponseDataMakanan;
import com.fiqri.gofoodsederhana.model.ResponseKategori;
import com.fiqri.gofoodsederhana.model.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<ResponseRegister> registerrr(
            @Field("vsnama") String nama,
            @Field("vsalamat") String alamat,
            @Field("vsjenkel") String jenkel,
            @Field("vsnotelp") String noHp,
            @Field("vsusername") String userName,
            @Field("vslevel") String level,
            @Field("vspassword") String password);

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<ResponseRegister> loginnn(
            @Field("edtusername") String userName,
            @Field("edtpassword") String password,
            @Field("vslevel") String level);

    @GET("kategorimakanan.php")
    Call<ResponseKategori> getDataKategori();

    @FormUrlEncoded
    @POST("getdatamakanan.php")
    Call<ResponseDataMakanan> getDataMakanan(
            @Field("vsiduser") String idUser,
            @Field("vsidkastrkategorimakanan") String dataKategori);
}
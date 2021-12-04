package com.rizqi.lumecolorsapp.api

import com.rizqi.lumecolorsapp.response.*
import retrofit2.Call
import retrofit2.http.*

interface GetDataService {

    @FormUrlEncoded
    @POST("account/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("periode") periode: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("loading_in/history")
    fun listHistory(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseHistory>

    @FormUrlEncoded
    @POST("loading_in/simpan")
    fun loadingInSave(
        @Field("id_produk") id_produk: String,
        @Field("tgl") tgl: String,
        @Field("no_delivery") no_delivery: String,
        @Field("no_batch") no_batch: String,
        @Field("exp_date") exp_date: String,
        @Field("qty_lolos") qty_lolos: String,
        @Field("qty_reject") qty_reject: String,
        @Field("insert_by") insert_by: String,
        @Field("insert_dt") insert_dt: String,
    ): Call<ResponseHistory>

    @FormUrlEncoded
    @POST("stok/in")
    fun stokIn(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseStok>

    @FormUrlEncoded
    @POST("stok/out")
    fun stokOut(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseStok>

    @FormUrlEncoded
    @POST("stok/opname")
    fun opname(
        @Field("periode") periode: String,
    ): Call<ResponseOpname>

    @FormUrlEncoded
    @POST("loading_in/qr")
    fun listQr(
        @Field("id_loading_in") id_loading_in: String,
    ): Call<ResponseListQR>

//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("stok/detail")
    fun detailStok(
        @Field("id") id: String,
    ): Call<ResponseStokDetail>

    @FormUrlEncoded
    @POST("approve_out/history")
    fun approveOutHistory(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("master/produk")
    fun dataProduk(): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/get_qr")
    fun approveOutQR(
        @Field("id_produk") id_produk: String,
    ): Call<ResponseApprove>
}
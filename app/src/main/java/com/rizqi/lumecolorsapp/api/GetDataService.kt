package com.rizqi.lumecolorsapp.api

import com.rizqi.lumecolorsapp.response.ResponseHistory
import com.rizqi.lumecolorsapp.response.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface GetDataService {

    @FormUrlEncoded
    @POST("account/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
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
}
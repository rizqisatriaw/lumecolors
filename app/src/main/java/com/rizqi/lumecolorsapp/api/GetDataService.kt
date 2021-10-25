package com.rizqi.lumecolorsapp.api

import com.rizqi.lumecolorsapp.response.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface GetDataService {

    @FormUrlEncoded
    @POST("account/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("people/1/")
    fun userStar (): Call<ResponseLogin>
}
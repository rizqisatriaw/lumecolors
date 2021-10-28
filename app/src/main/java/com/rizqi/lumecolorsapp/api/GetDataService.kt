package com.rizqi.lumecolorsapp.api

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
}
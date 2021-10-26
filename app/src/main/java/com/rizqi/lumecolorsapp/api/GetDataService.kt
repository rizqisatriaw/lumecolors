package com.rizqi.lumecolorsapp.api

import com.rizqi.lumecolorsapp.model.MUser
import com.rizqi.lumecolorsapp.response.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface GetDataService {

    @POST("account/login")
    fun userLogin(
        @Body user: MUser
    ): Call<ResponseLogin>
}
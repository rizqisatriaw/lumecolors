package com.rizqi.lumecolorsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClients {
    fun getRetrofitInstance(): Retrofit {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://tes.smilink.id/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}
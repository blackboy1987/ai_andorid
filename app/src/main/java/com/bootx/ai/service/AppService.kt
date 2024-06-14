package com.bootx.ai.service

import com.bootx.ai.entity.AppEntityResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface AppService {

    @POST("/api/categoryApp/config")
    @FormUrlEncoded
    suspend fun config(
        @Header("token") token: String,
        @Field("deviceId") deviceId: String,
        @Field("id") id: Int,
    ): AppEntityResponse


    companion object {
        fun instance(): AppService {
            return HiRetrofit.create(AppService::class.java)
        }
    }
}
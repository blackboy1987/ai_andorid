package com.bootx.ai.service

import com.bootx.ai.entity.AppEntityResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface AppService {

    @POST("/api/member/categoryApp/config")
    @FormUrlEncoded
    suspend fun config(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("id") id: Int,
    ): AppEntityResponse

    @POST("/api/member/categoryApp/write")
    @FormUrlEncoded
    suspend fun write(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("categoryAppId") categoryAppId: Int,
        @Field("params") params: String,
    ): AppEntityResponse
    companion object {
        fun instance(): AppService {
            return HiRetrofit.create(AppService::class.java)
        }
    }
}
package com.bootx.ai.service

import com.bootx.ai.entity.CommonResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface LoginService {

    @POST("/api/member/sendCode")
    @FormUrlEncoded
    suspend fun sendCode(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("mobile") mobile: String,
    ): CommonResponse

    @POST("/api/member/login")
    @FormUrlEncoded
    suspend fun login(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("mobile") mobile: String,
        @Field("code") code: String,
    ): CommonResponse

    companion object {
        fun instance(): LoginService {
            return HiRetrofit.create(LoginService::class.java)
        }
    }
}
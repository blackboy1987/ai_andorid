package com.bootx.ai.service

import com.bootx.ai.entity.CommonResponse
import com.bootx.ai.entity.ImageAppEntityResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface ImageService {

    @POST("/api/member/imageApp/config")
    suspend fun config(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
    ): ImageAppEntityResponse

    @POST("/api/member/text2image")
    @FormUrlEncoded
    suspend fun create(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("prompt") prompt: String,
        @Field("style") style: String,
        @Field("size") size: String,
    ): CommonResponse

    @POST("/api/member/task")
    @FormUrlEncoded
    suspend fun task(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("taskId") taskId: String,
    ): CommonResponse

    companion object {
        fun instance(): ImageService {
            return HiRetrofit.create(ImageService::class.java)
        }
    }
}
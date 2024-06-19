package com.bootx.ai.service

import com.bootx.ai.entity.ImageAppEntityResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Header
import retrofit2.http.POST


interface ImageService {

    @POST("/api/member/imageApp/config")
    suspend fun config(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
    ): ImageAppEntityResponse

    companion object {
        fun instance(): ImageService {
            return HiRetrofit.create(ImageService::class.java)
        }
    }
}
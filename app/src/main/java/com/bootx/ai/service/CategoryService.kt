package com.bootx.ai.service

import com.bootx.ai.entity.CategoryEntityResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface CategoryService {

    @POST("/api/member/category/list")
    @FormUrlEncoded
    suspend fun list(
        @Header("token") token: String,
        @Field("deviceId") deviceId: String,
    ): CategoryEntityResponse


    companion object {
        fun instance(): CategoryService {
            return HiRetrofit.create(CategoryService::class.java)
        }
    }
}
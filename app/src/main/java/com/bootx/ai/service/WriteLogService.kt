package com.bootx.ai.service

import com.bootx.ai.entity.CategoryAppTaskDetailResponse
import com.bootx.ai.entity.CategoryAppTaskEntityListResponse
import com.bootx.ai.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface WriteLogService {

    @POST("/api/member/categoryAppTask/list")
    @FormUrlEncoded
    suspend fun list(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("id") id: Int,
    ): CategoryAppTaskEntityListResponse

    @POST("/api/member/categoryAppTask/detail")
    @FormUrlEncoded
    suspend fun detail(
        @Header("token") token: String,
        @Header("deviceId") deviceId: String,
        @Field("taskId") taskId: String,
    ): CategoryAppTaskDetailResponse



    companion object {
        fun instance(): WriteLogService {
            return HiRetrofit.create(WriteLogService::class.java)
        }
    }
}
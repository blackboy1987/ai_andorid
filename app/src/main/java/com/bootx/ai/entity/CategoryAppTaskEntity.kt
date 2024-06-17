package com.bootx.ai.entity

data class CategoryAppTaskEntity(
    val id: Int=0,
    val taskId: String="",
    val status: Int = -1,
    val appName: String="",
    val taskEndDate: String="",
    val appLogo: String="",
)

data class CategoryAppTaskDetail(
    val status: Int=0,
    val params: String="",
    val taskBeginDate: String="",
    val taskEndDate: String="",
    val title: String="",
    val content: String="",
    val inputTokens: Int=0,
    val totalTokens: Int=0,
    val outputTokens: Int=0,
)


data class CategoryAppTaskEntityListResponse(val data: List<CategoryAppTaskEntity>) : BaseResponse()
data class CategoryAppTaskDetailResponse(val data: CategoryAppTaskDetail) : BaseResponse()
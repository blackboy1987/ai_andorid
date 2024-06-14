package com.bootx.ai.entity

data class AppEntity(
    val id: Int=0,
    val title: String="",
    val thumb: String="",
    val memo: String="",
)
data class AppEntityResponse(val data: List<AppEntity>) : BaseResponse()
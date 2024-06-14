package com.bootx.ai.entity

data class CategoryEntity(
    val id: Int=0,
    val name: String="",
    val apps: List<AppEntity> = listOf(),
)
data class CategoryEntityResponse(val data: List<CategoryEntity>) : BaseResponse()
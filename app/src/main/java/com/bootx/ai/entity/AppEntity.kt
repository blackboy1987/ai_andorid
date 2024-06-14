package com.bootx.ai.entity

data class AppEntity(
    val id: Int=0,
    val title: String="",
    val thumb: String="",
    val memo: String="",
    var formDataList: List<FormDataList> = listOf()
)

data class FormDataList(
    val label: String="",
    val formType: String="",
    val isRequired: Boolean=false,
    val placeholder: String="",
    val options: List<String> = listOf(),
)

data class AppEntityListResponse(val data: List<AppEntity>) : BaseResponse()
data class AppEntityResponse(val data: AppEntity) : BaseResponse()
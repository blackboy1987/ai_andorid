package com.bootx.ai.entity

data class TextMessageEntity(
    val requestId: String="",
    val inputTokens: String="",
    val outputTokens: String="",
    val totalTokens: String="",
    val finishReason: String="",
    val role: String="",
    var content: String="",
    var isUser:Boolean = false,
)
data class TextMessageEntityResponse(val data: List<TextMessageEntity>) : BaseResponse()
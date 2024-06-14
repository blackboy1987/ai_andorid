package com.bootx.ai.entity

data class TextMessageEntity(
    val requestId: String="",
    val inputTokens: String="",
    val outputTokens: String="",
    val totalTokens: String="",
    val finishReason: String="",
    val role: String="",
    val content: String="",
)
data class TextMessageEntityResponse(val data: List<TextMessageEntity>) : BaseResponse()
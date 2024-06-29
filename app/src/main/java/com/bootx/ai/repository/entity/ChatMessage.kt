package com.bootx.ai.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ChatMessage"
)
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var content: String="",
    val role: Int = 0,
)
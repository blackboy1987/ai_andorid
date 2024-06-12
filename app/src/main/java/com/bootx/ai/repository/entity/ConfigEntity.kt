package com.bootx.ai.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "config"
)
data class ConfigEntity(
    @PrimaryKey
    var id: Int=0,
    var ticketMax: Int=100,
)
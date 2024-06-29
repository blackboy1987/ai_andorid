package com.bootx.ai.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bootx.ai.repository.entity.ChatMessage
import com.bootx.ai.repository.entity.ConfigEntity

@Dao
interface ChatMessageDao {

    @Query("select * from ChatMessage order by id asc")
    fun getAll(): List<ChatMessage>

    @Insert
    fun insert(chatMessage: ChatMessage)

    @Update
    fun update(chatMessage: ChatMessage)
    @Query("select * from ChatMessage where id = :id")
    fun getById(id: Int): ChatMessage

    @Query("delete from ChatMessage where 1=1")
    fun delete()
}
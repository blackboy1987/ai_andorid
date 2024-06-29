package com.bootx.ai.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bootx.ai.repository.dao.ChatMessageDao
import com.bootx.ai.repository.dao.ConfigDao
import com.bootx.ai.repository.entity.ChatMessage
import com.bootx.ai.repository.entity.ConfigEntity

@Database(
    version = 3,
    entities = [
        ConfigEntity::class,
        ChatMessage::class
    ],
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    companion object {
        private var db: DataBase? = null
        private var name = "id"
        fun getDb(context: Context) = if (db == null) {
            Room.databaseBuilder(context, DataBase::class.java, name)
                .enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build().apply {
                db = this
            }
        } else {
            db
        }
    }
    abstract fun getConfigDao(): ConfigDao
    abstract fun getChatMessageDao(): ChatMessageDao

}
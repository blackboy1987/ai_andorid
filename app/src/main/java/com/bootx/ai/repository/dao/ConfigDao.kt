package com.bootx.ai.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bootx.ai.repository.entity.ConfigEntity

@Dao
interface ConfigDao {

    @Query("select * from config order by id desc")
    fun getAll(): List<ConfigEntity>

    @Insert
    fun insert(configEntity: ConfigEntity)

    @Update
    fun update(historyEntity: ConfigEntity)
    @Query("select * from config where id = :id")
    fun getById(id: Int): ConfigEntity

    @Query("delete from config where 1=1")
    fun delete()
}
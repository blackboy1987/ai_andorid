package com.bootx.ai.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class SharedPreferencesUtils(private val context: Context) {
    fun set(key: String,value: String){
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString(key,value)
        edit.apply()
    }

    fun get(key: String): String {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key,"") ?: ""
    }

    fun getCountDown(): Long {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val s = sharedPreferences.getString("countDown", "180") ?: "180"
        return s.toLong()
    }

    fun setCountDown(countDown:Long) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("countDown","$countDown")
        edit.apply()
    }

    fun getRemainTicket(): Int {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val s = sharedPreferences.getString("remainTicket", "7") ?: "7"
        return s.toInt()
    }

    fun setRemainTicket(remainTicket:Int) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("remainTicket","$remainTicket")
        edit.apply()
    }

    fun decreaseRemainTicket(count: Int){
        setRemainTicket(getRemainTicket()-count)
    }


    fun setLevelId(levelId:Int) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("levelId","$levelId")

        if(getMaxLevelId()<levelId){
            setMaxLevelId(levelId)
        }

        edit.apply()
    }

    fun getLevelId(): Int {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val s = sharedPreferences.getString("levelId", "1") ?: "1"
        return s.toInt()
    }

    fun getMaxLevelId(): Int {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val s = sharedPreferences.getString("maxLevelId", "1") ?: "1"
        return s.toInt()
    }
    fun setMaxLevelId(levelId:Int) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("maxLevelId","$levelId")
        edit.apply()
    }

    fun remove(key: String?) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        if(key!=null){
            edit.remove(key)
        }else{
            edit.clear()
        }
        edit.apply()
    }

    fun addLevelId() {
        val levelId = getLevelId()
        setLevelId(levelId+1)
    }


    fun getToken(): String {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("token", "") ?: ""
    }

    fun setToken(token:String) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("token", token)
        edit.apply()
    }

    fun getDeviceId(): String {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("deviceId", "") ?: ""
    }

    fun setDeviceId(deviceId:String) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("deviceId", deviceId)
        edit.apply()
    }


}
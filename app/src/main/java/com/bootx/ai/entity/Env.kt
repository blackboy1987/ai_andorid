package com.bootx.ai.entity

data class Env(
    var isUsbDebuggingEnabled: Boolean=true,
    var simCardStatus:Int = 0,
    var model: String = "",
    var os: String = "",
    var manufacturer: String = "",
    var token: String = "",
    var appId: String = "",
    var deviceId: String = "",
)
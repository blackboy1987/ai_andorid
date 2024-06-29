package com.bootx.ai.util

import okhttp3.*
import java.io.IOException

class EventStreamManager(private val url: String, private val onMessage: (String) -> Unit) {
    private val client = OkHttpClient()
    private var call: Call? = null

    fun start() {
        val request = Request.Builder().url(url).build()
        call = client.newCall(request)

        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    response.close()
                    return
                }

                val source = response.body?.source()
                source?.use {
                    while (!it.exhausted()) {
                        val line = it.readUtf8Line()
                        line?.let { onMessage(it) }
                    }
                }
            }
        })
    }

    fun stop() {
        call?.cancel()
    }
}

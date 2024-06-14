package com.bootx.ai.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class SSEClient(
    private val baseUrl: String
) {
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private val _events = Channel<String>()
    val events = _events.receiveAsFlow()
    private var call: Call? = null

    fun connect(parameters: Map<String, String>,onEvent: (String) -> Unit) {
        val urlBuilder = baseUrl.toHttpUrlOrNull()?.newBuilder()
        parameters.forEach { (key, value) ->
            urlBuilder?.addQueryParameter(key, value)
        }
        val url = urlBuilder?.build().toString()

        val request = Request.Builder()
            .url(url)
            .header("appId","123456")
            .build()
        call = client.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _events.trySend("Error: ${e.message}").isSuccess
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    _events.trySend("Error: ${response.message}").isSuccess
                    return
                }

                response.body?.let { responseBody ->
                    val source = responseBody.source()
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line()
                        line?.let {
                            if (it.startsWith("data:")) {
                                val message = it.removePrefix("data:").trim()
                                onEvent(message)
                                _events.trySend(message).isSuccess
                            }
                        }
                    }
                }
            }
        })
    }

    fun disconnect() {
        call?.cancel()
    }
}


package com.bootx.ai.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bootx.ai.util.EventStreamManager
import kotlinx.coroutines.launch

@Composable
fun DemoScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }
    var eventStreamManager by remember { mutableStateOf<EventStreamManager?>(null) }

    DisposableEffect(Unit) {
        eventStreamManager?.start()
        onDispose { eventStreamManager?.stop() }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text(text = message)
            Spacer(modifier = Modifier.height(20.dp))
        }
       item{
           Button(onClick = {
               if (eventStreamManager == null) {
                   eventStreamManager = EventStreamManager("http://192.168.31.214:9902/api/member1/message?content=自我介绍") { data ->
                       scope.launch {
                           message += data.replace("data:","")
                           Log.e("eventStreamManager", "DemoScreen: $message", )
                       }
                   }
                   eventStreamManager?.start()
               }
           }) {
               Text("Start Event Stream")
           }
           Spacer(modifier = Modifier.height(20.dp))
       }
        item{
            Button(onClick = {
                eventStreamManager?.stop()
                eventStreamManager = null
            }) {
                Text("Stop Event Stream")
            }
        }
    }
}

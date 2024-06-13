package com.bootx.ai.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(
    height:Int = 200,
    title: String = "",
    confirmText: String = "",
    cancelText: String = "",
    content: @Composable () -> Unit
) {
    BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                if (title.isNotBlank()) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
                Box(
                    modifier = Modifier
                        .height(height.dp),
                ) {
                    LazyColumn {
                        item {
                            content()
                        }
                    }
                }
                if (confirmText.isNotEmpty() || cancelText.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (cancelText.isNotEmpty()) {
                            OutlinedButton(
                                modifier = Modifier.weight(1.0f),
                                onClick = {

                                }) {
                                Text(
                                    text = cancelText,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                            }
                        }
                        if(confirmText.isNotEmpty()&&confirmText.isNotEmpty()){
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        if (confirmText.isNotEmpty()) {
                            Button(
                                modifier = Modifier.weight(1.0f),
                                onClick = { /*TODO*/ }) {
                                Text(
                                    text = confirmText,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
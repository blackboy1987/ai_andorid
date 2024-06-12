package com.bootx.ai.ui.components.ad

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.Date

/**
 *横幅广告
 */
@Composable
fun LoadAdRandom(context: Context) {
    var type by remember {
        mutableIntStateOf((Date().time%3).toInt())
    }
    if(type==0){
        RequestExpressDrawFeedAd(context){status->
            if(status=="onError"){
                type = 1
            }
        }
    }else if(type==1){
        RequestExpressAd(context){status->
            if(status=="onError"){
                type = 1
            }
        }
    }else if(type==2){
        RequestBannerAd(context){status->
            if(status=="onError"){
                type = 3
            }
        }
    }
}
@Composable
fun LoadAd(context: Context,type:Int) {
    when(type){
        0-> RequestExpressDrawFeedAd(context){

        }
        1-> RequestExpressAd(context){

        }
        2-> RequestBannerAd(context){
        }
    }
}

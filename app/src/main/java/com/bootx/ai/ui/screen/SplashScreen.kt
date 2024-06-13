import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.bootx.ai.config.Config
import com.bootx.ai.ui.components.ad.RequestSplashAd
import com.bootx.ai.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(callback:(status:Int)->Unit) {
    val context = LocalContext.current
    var ticks by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while(ticks<100) {
            delay(200)
            ticks++
        }
    }
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = "${Config.imageUrl}bg_main.png",
                contentDescription = ""
            )
            LinearProgressIndicator(progress = { (ticks+0.0f)/100 }, modifier = Modifier.fillMaxWidth())
        }
    }
    RequestSplashAd(context) { status ->
        Log.e("SplashScreen", "RequestSplashAd: $status", )
        if (status === "onError") {
            callback(-1)
        } else if (status == "onAdShow") {
            callback(1)
        } else {
            callback(2)
        }
    }


}
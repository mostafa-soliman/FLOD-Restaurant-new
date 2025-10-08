package com.fadali.flod_restaurant.presentation.ui.screens.loading

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.fadali.flod_restaurant.R
import kotlinx.coroutines.delay
import androidx.core.net.toUri

data class StepItem(
    val imageRes: Any,
    val textId: Int
)

@Composable
fun PreparingScreen(
    steps: List<StepItem> = listOf(
        StepItem(R.raw.onboarding_rocket, R.string.preparing_profile),
        StepItem(R.raw.onboarding_circles, R.string.calculating_calories),
        StepItem(R.raw.onboarding_pencil, R.string.getting_plans)
    ),
    intervalMs: Long = 3000L,
    onFinish: () -> Unit = {}
) {
    var stepIndex by remember { mutableStateOf(0) }

    // auto-switch
    LaunchedEffect(stepIndex) {
        if (stepIndex < steps.lastIndex) {
            delay(intervalMs)
            stepIndex++
        } else {
            delay(intervalMs)
            onFinish()
        }
    }
    val context = LocalContext.current

    val imageLoader = remember(context) {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    val current = steps[stepIndex]
    val uri = "android.resource://${context.packageName}/${current.imageRes}".toUri()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            AsyncImage(
                model = uri,
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier.size(210.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(id = current.textId),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showSystemUi = true, locale = "en", name = "EN")
@Composable
fun PreviewPreparingEn() {
    PreparingScreen()
}

@Preview(showSystemUi = true, locale = "ar", name = "AR")
@Composable
fun PreviewPreparingAr() {
    PreparingScreen()
}

@Preview(
    name = "AR - Dark",
    showSystemUi = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPreparingArDark() {
    PreparingScreen()
}

package com.fadali.flod_restaurant.feature.auth.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.video.videoplayer.VideoPlayer
import com.fadali.flod_restaurant.presentation.ui.theme.PrimaryRed
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.onErrorText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
) {
    val letterCount = stringResource(R.string.brand).length
    val letterAnimations = remember { List(letterCount) { Animatable(0f) } }
    val underlineAnimation = remember { Animatable(0f) }
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/${R.raw.splash_video}".toUri()
    var showVideo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500L)
        letterAnimations.forEachIndexed { index, animatable ->
            delay(100L * index)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        underlineAnimation.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
        delay(500L)
        showVideo = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryRed),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Brand text with letter-by-letter animation
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                val letters = stringResource(R.string.brand).toList().map { it.toString() }
                letters.forEachIndexed { index, letter ->
                    val animationValue = letterAnimations[index].value
                    Text(
                        text = letter,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorText,
                        ),
                        modifier = Modifier
                            .offset(y = (-100 * (1 - animationValue)).dp)
                            .alpha(animationValue)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Animated underline
            Box(
                modifier = Modifier
                    .width((120 * underlineAnimation.value).dp)
                    .height(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.accentBlue,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }

    if (showVideo) {
        VideoPlayer(
            modifier = Modifier.fillMaxWidth(),
            uri = videoUri,
            showControllers = false,
            onVideoEnded = {
                navController.navigate(AuthRoutes.Login) {
                    popUpTo(AuthRoutes.Splash) { inclusive = true }
                }
            }
        )
    }
}

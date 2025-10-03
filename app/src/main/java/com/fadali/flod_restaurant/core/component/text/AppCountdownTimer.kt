package com.fadali.flod_restaurant.core.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun AppCountdownTimer(
    modifier: Modifier = Modifier,
    initialTimeInSeconds: Long = 60,
    onTimerFinished: () -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    isRunning: Boolean = true
) {
    var timeLeft by rememberSaveable(isRunning) { mutableLongStateOf(initialTimeInSeconds) }
    var isRunning by rememberSaveable(isRunning) { mutableStateOf(isRunning) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            isRunning = false
            onTimerFinished()
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

    Text(
        modifier = modifier,
        text = timeDisplay,
        style = textStyle.copy(
            color = when {
                !isRunning -> MaterialTheme.colorScheme.grayText
                else -> textStyle.color
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppCountdownTimerPreview() {
    FLODRestaurantTheme {
        AppCountdownTimer()
    }
}

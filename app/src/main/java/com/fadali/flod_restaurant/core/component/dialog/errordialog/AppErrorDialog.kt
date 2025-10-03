package com.fadali.flod_restaurant.core.component.dialog.errordialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.button.AppSecondaryButton
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppErrorDialog(
    isVisible: Boolean,
    title: String,
    message: String,
    primaryButtonText: String,
    secondaryButtonText: String? = null,
    isDismissible: Boolean = true,
    onPrimaryBtnClicked: () -> Unit,
    onSecondaryBtnClicked: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var isDialogVisible by rememberSaveable(isVisible) { mutableStateOf(isVisible) }
    if (!isDialogVisible) return

    BasicAlertDialog(
        onDismissRequest = {
            if (isDismissible) {
                onDismiss()
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = isDismissible,
            dismissOnClickOutside = isDismissible
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppPrimaryButton(
                    onClick = {
                        onPrimaryBtnClicked()
                        onDismiss()
                    },
                    text = primaryButtonText,
                )
                if (secondaryButtonText != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    AppSecondaryButton(
                        onClick = {
                            onSecondaryBtnClicked()
                            onDismiss()
                        },
                        text = secondaryButtonText,
                        showBorder = false
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppErrorDialogPreview() {
    FLODRestaurantTheme {
        AppErrorDialog(
            isVisible = true,
            title = "Error Occurred",
            message = "Unable to connect to the server. Please check your internet connection and try again.",
            primaryButtonText = "Try Again",
            secondaryButtonText = "Cancel",
            isDismissible = true,
            onPrimaryBtnClicked = {},
            onSecondaryBtnClicked = {},
            onDismiss = {}
        )
    }
}

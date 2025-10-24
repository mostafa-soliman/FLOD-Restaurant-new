package com.fadali.flod_restaurant.core.component.result.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.core.util.toUri
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultBottomSheet(
    bottomSheetResult: BottomSheetResult,
    isVisible: Boolean = true,
    isDismissable: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    if (!isVisible) return

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetState ->
            if (!isDismissable) {
                sheetState != SheetValue.Hidden
            } else {
                true
            }
        }
    )
    val imageLoader = remember(context) {
        ImageLoader.Builder(context)
            .components { add(ImageDecoderDecoder.Factory()) }
            .build()
    }
    val image = if (bottomSheetResult is BottomSheetResult.Success) R.raw.gif_success.toUri(context)
    else R.raw.gif_failure.toUri(context)

    val imageContentDesc = if (bottomSheetResult is BottomSheetResult.Success) R.string.success
    else R.string.failure

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .width(134.dp)
                    .height(5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(size = 50.dp)
                    )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(if (bottomSheetResult is BottomSheetResult.Success) 164.dp else 107.dp),
                model = image,
                contentDescription = stringResource(imageContentDesc),
                imageLoader = imageLoader
            )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = bottomSheetResult.message.resolve(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            )
            AppPrimaryButton(
                modifier = Modifier.padding(bottom = 16.dp),
                text = bottomSheetResult.primaryActionText.resolve(),
                onClick = {
                    onDismiss()
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        bottomSheetResult.onPrimaryActionClicked()
                    }
                }
            )
        }
    }

}

@Preview
@Composable
private fun ResultBottomSheetPreview() {
    FLODRestaurantTheme {
        ResultBottomSheet(
            bottomSheetResult = BottomSheetResult.Success(
                message = DeferredString.StringResource(R.string.payment_success_message),
                primaryActionText = DeferredString.StringResource(R.string.ok),
                onPrimaryActionClicked = {}
            ),
            onDismiss = {}
        )
    }
}

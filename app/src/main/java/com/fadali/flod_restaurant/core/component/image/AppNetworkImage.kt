package com.fadali.flod_restaurant.core.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.fadali.flod_restaurant.presentation.ui.components.ShimmerBox

@Composable
fun AppNetworkImage(
    model: Any?,
    modifier: Modifier = Modifier,
    shape: Shape? = RoundedCornerShape(6.dp),
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    val context = LocalContext.current
    val request = remember(model) {
        ImageRequest.Builder(context)
            .data(model)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    val inPreview = LocalInspectionMode.current
    val mod = if (shape != null) modifier.clip(shape) else modifier
    if (inPreview) {
        Box(mod.background(Color(0xFFEAEAEA)))
        return
    }

    SubcomposeAsyncImage(
        model = request,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = mod,
        loading = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) },
        error = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) }
    )
}

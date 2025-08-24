package com.fadali.flod_restaurant.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    shape: Shape? = RoundedCornerShape(16.dp),
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
) {
    val context = LocalContext.current
    val request = remember(url) {
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    val mod = if (shape != null) modifier.clip(shape) else modifier

    SubcomposeAsyncImage(
        model = request,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = mod,
        loading = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) },
        error = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) }
    )
}

//@Composable
//fun NetworkImage(
//    url: String,
//    modifier: Modifier = Modifier,
//    shape: Shape? = RoundedCornerShape(16.dp),
//    contentScale: ContentScale = ContentScale.Crop,
//    contentDescription: String? = null,
//) {
//    val context = LocalContext.current
//    val request = remember(url) {
//        ImageRequest.Builder(context)
//            .data(url)
//            .crossfade(true)
//            .diskCachePolicy(CachePolicy.ENABLED)
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .networkCachePolicy(CachePolicy.ENABLED)
//            .build()
//    }
//    val mod = if (shape != null) modifier.clip(shape) else modifier
//
//    SubcomposeAsyncImage(
//        model = request,
//        contentDescription = contentDescription,
//        contentScale = contentScale,
//        modifier = mod,
//        loading = {
//            ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0))
//        },
//        error = { state ->
//            LaunchedEffect(state) {
//                val err = state.result.throwable
//                android.util.Log.e("NetworkImage", "Load failed for: $url", err)
//            }
//            Box(Modifier.fillMaxSize().background(Color(0x11000000)))
//        }
//    )
//}

//@Composable
//fun NetworkImage(
//    url: String,
//    modifier: Modifier = Modifier,
//    shape: Shape? = RoundedCornerShape(Dimens.CardRadius),
//    contentScale: ContentScale = ContentScale.Crop,
//    contentDescription: String? = null,
//) {
//    val mod = if (shape != null) modifier.clip(shape) else modifier
//    SubcomposeAsyncImage(
//        model = url,
//        contentDescription = contentDescription,
//        contentScale = contentScale,
//        modifier = mod,
//        loading = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) },
//        error   = { ShimmerBox(Modifier.fillMaxSize(), shape ?: RoundedCornerShape(0)) }
//    )
//}

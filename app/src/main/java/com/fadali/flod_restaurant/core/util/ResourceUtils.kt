package com.fadali.flod_restaurant.core.util

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.net.toUri

fun @receiver:RawRes Int.toUri(context: Context): Uri {
    return "android.resource://${context.packageName}/${this}".toUri()
}

fun String.toAssetUrl(): String {
    return "file:///android_asset/$this"
}

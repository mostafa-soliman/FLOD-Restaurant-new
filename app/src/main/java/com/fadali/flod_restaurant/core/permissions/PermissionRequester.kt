package com.fadali.flod_restaurant.core.permissions

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionRequester {
    const val REQUEST_CODE = 101
    private val permissions: Array<String> = arrayOf(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.POST_NOTIFICATIONS else null
    ).filterNotNull().toTypedArray<String>()

    fun requestPermissions(activity: Activity) {
        if (permissions.isEmpty()) return
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            REQUEST_CODE
        )
    }
}

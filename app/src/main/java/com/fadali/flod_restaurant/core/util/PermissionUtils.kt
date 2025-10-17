package com.fadali.flod_restaurant.core.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object PermissionUtils {

    val LOCATION_PERMISSIONS = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ).toTypedArray()

    @Composable
    fun getPermissionsActivityResultLauncher(
        onPermissionsGranted: () -> Unit = {},
        onPermissionsDenied: () -> Unit = {},
        onSomePermissionsGranted: () -> Unit = {},
    ) = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsGrantedMap ->
        val allPermissionsGranted = permissionsGrantedMap.values.all { it }
        if (allPermissionsGranted) {
            onPermissionsGranted()
            return@rememberLauncherForActivityResult
        }
        val allPermissionsDenied = permissionsGrantedMap.values.all { !it }
        if (allPermissionsDenied) {
            onPermissionsDenied()
            return@rememberLauncherForActivityResult
        }
        val anyPermissionGranted = permissionsGrantedMap.values.any { it }
        if (anyPermissionGranted) {
            onSomePermissionsGranted()
        }
    }

    @Composable
    fun RequestLocationPermissions(
        onPermissionsGranted: () -> Unit = {},
        onPermissionsDenied: () -> Unit = {},
        onSomePermissionsGranted: () -> Unit = {},
    ) {
        val requiredLocationPermissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).toTypedArray()

        val locationPermissionLauncher = getPermissionsActivityResultLauncher(
            onPermissionsGranted = onPermissionsGranted,
            onSomePermissionsGranted = onSomePermissionsGranted,
            onPermissionsDenied = onPermissionsDenied
        )
        locationPermissionLauncher.launch(requiredLocationPermissions)
    }

    fun openAppSettings(context: Context, newTask: Boolean = true) {
        context.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts(
                    "package",
                    context.packageName,
                    null
                ),
            ).apply {
                if (newTask && context !is Activity) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
        )
    }
}

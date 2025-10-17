package com.fadali.flod_restaurant.core.util

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.util.MapsUtils.getDetailedAddressFromLocation
import com.fadali.flod_restaurant.core.util.MapsUtils.requestCurrentLocationUpdate
import com.fadali.flod_restaurant.core.util.MapsUtils.stopLocationUpdates
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object MapsUtils {

    /**
     * Data class to hold detailed address components
     */
    data class DetailedAddress(
        val fullAddress: String,
        val streetName: String,
        val streetNumber: String,
        val district: String, // Also known as subLocality or neighborhood
        val city: String, // Also known as locality
        val state: String, // Also known as administrative area
        val country: String,
        val postalCode: String
    )

    /**
     * Creates a Google Maps URI for opening a specific location with a label
     * @param latitude The latitude coordinate
     * @param longitude The longitude coordinate
     * @param label The label to display for the location
     * @return URI that can be used with Intent to open Google Maps
     */
    fun getMapsUri(latitude: Double, longitude: Double, label: String): Uri {
        return "geo:$latitude,$longitude?q=$latitude,$longitude($label)".toUri()
    }

    /**
     * Helper function to convert drawable resource to BitmapDescriptor for Google Maps markers
     * @param drawableId Resource ID of the drawable to convert
     * @return BitmapDescriptor that can be used for custom map markers
     */
    @Composable
    fun getBitmapDescriptorFromDrawable(drawableId: Int): BitmapDescriptor {
        val context = LocalContext.current
        val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = drawable?.toBitmap(
            width = drawable.intrinsicWidth,
            height = drawable.intrinsicHeight
        )
        return BitmapDescriptorFactory.fromBitmap(bitmap!!)
    }

    /**
     * Extension function to convert Google Play Services Task to Kotlin coroutine
     * Suspends the current coroutine until the task completes
     * @param T The type of result returned by the task
     * @return The result of the completed task
     * @throws Exception if the task fails
     */
    suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (task.exception != null) {
                continuation.resumeWithException(task.exception!!)
            } else {
                continuation.resume(task.result)
            }
        }
    }

    /**
     * Calculate Haversine distance in kilometers between two coordinates
     * Uses the Haversine formula to calculate the great-circle distance between two points
     * on the Earth's surface given their latitude and longitude coordinates
     * @param fromLocation Starting location coordinates
     * @param toLocation Destination location coordinates
     * @return Distance in kilometers between the two locations
     */
    fun calculateDistanceInKilometers(fromLocation: LatLng, toLocation: LatLng): Double {
        val earthRadiusInKilometers = 6372.8
        fun Double.convertToRadians() = Math.toRadians(this)

        val deltaLatitude = (toLocation.latitude - fromLocation.latitude).convertToRadians()
        val deltaLongitude = (toLocation.longitude - fromLocation.longitude).convertToRadians()
        val firstLocationLatitudeRadians = fromLocation.latitude.convertToRadians()
        val secondLocationLatitudeRadians = toLocation.latitude.convertToRadians()

        val haversineFormulaPartA = sin(deltaLatitude / 2) * sin(deltaLatitude / 2) +
                sin(deltaLongitude / 2) * sin(deltaLongitude / 2) * cos(firstLocationLatitudeRadians) * cos(
            secondLocationLatitudeRadians
        )
        val haversineFormulaPartC = 2 * asin(sqrt(haversineFormulaPartA))
        val distanceInKilometers = earthRadiusInKilometers * haversineFormulaPartC

        return distanceInKilometers
    }

    /**
     * Check if location permission is granted
     * Checks for both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions
     * @param context Application context to check permissions
     * @return true if either fine or coarse location permission is granted, false otherwise
     */
    fun isLocationPermissionGranted(context: Context): Boolean {
        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fineLocationPermissionGranted || coarseLocationPermissionGranted
    }

    /**
     * Get best available user location using FusedLocationProviderClient
     * First tries to get current location with high accuracy, then falls back to last known location
     * @param context Application context
     * @return LatLng of user's location, or null if location cannot be determined
     * @throws SecurityException if location permissions are not granted
     */
    @SuppressLint("MissingPermission")
    suspend fun getBestAvailableUserLocation(context: Context): LatLng? {
        val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val currentUserLocation = try {
            locationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .await()
        } catch (_: Exception) {
            null
        }
        if (currentUserLocation != null) {
            return LatLng(
                currentUserLocation.latitude,
                currentUserLocation.longitude
            )
        }

        val lastKnownUserLocation = try {
            locationProviderClient.lastLocation.await()
        } catch (_: Exception) {
            null
        }
        return lastKnownUserLocation?.let { LatLng(it.latitude, it.longitude) }
    }

    /**
     * Request continuous location updates
     * Requests location updates with high accuracy priority every 5 seconds (minimum 2 seconds)
     * Note: Make sure to stop updates using stopLocationUpdates when no longer needed
     * @param context Application context
     * @param locationCallback Callback to receive location updates
     * @see stopLocationUpdates
     * Example usage:
     * ```
     * val locationUpdateCallback = object : LocationCallback() {
     *     override fun onLocationResult(locationResult: LocationResult) {
     *         locationResult.lastLocation?.let { lastLocation ->
     *             onLocationResult(LatLng(lastLocation.latitude, lastLocation.longitude))
     *         }
     *     }
     * }
     * ```
     */
    fun requestCurrentLocationUpdate(context: Context, locationCallback: LocationCallback) {
        val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (isLocationPermissionGranted(context)) {
            val locationUpdateRequest =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                    .setMinUpdateIntervalMillis(2000)
                    .build()

            try {
                locationProviderClient.requestLocationUpdates(
                    locationUpdateRequest,
                    locationCallback,
                    null
                )
            } catch (securityException: SecurityException) {
                // Handle permission exception silently
            }
        }
    }

    /**
     * Stop location updates to prevent memory leaks
     * Removes the specified location callback from receiving further updates
     * @param context Application context
     * @param locationCallback The same callback instance that was used in requestCurrentLocationUpdate
     * @see requestCurrentLocationUpdate
     */
    fun stopLocationUpdates(context: Context, locationCallback: LocationCallback) {
        val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationProviderClient.removeLocationUpdates(locationCallback)
    }

    /**
     * Get detailed address components from location coordinates
     * Returns separate address components like street name, city, district, etc.
     * @param context Application context
     * @param location LatLng coordinates
     * @param onResult Callback to handle the detailed address result
     */
    fun getDetailedAddressFromLocation(
        context: Context,
        location: LatLng,
        onResult: (DetailedAddress?) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) { addresses ->
                val detailedAddress = if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    DetailedAddress(
                        fullAddress = address.getAddressLine(0)
                            ?: context.getString(R.string.address_not_found),
                        streetName = address.thoroughfare ?: "",
                        streetNumber = address.subThoroughfare ?: "",
                        district = address.subLocality ?: "",
                        city = address.locality ?: "",
                        state = address.adminArea ?: "",
                        country = address.countryName ?: "",
                        postalCode = address.postalCode ?: ""
                    )
                } else {
                    null
                }
                onResult(detailedAddress)
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                val detailedAddress = if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    DetailedAddress(
                        fullAddress = address.getAddressLine(0)
                            ?: context.getString(R.string.address_not_found),
                        streetName = address.thoroughfare ?: "",
                        streetNumber = address.subThoroughfare ?: "",
                        district = address.subLocality ?: "",
                        city = address.locality ?: "",
                        state = address.adminArea ?: "",
                        country = address.countryName ?: "",
                        postalCode = address.postalCode ?: ""
                    )
                } else {
                    null
                }
                onResult(detailedAddress)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    /**
     * Coroutine-based version of getDetailedAddressFromLocation for easier async handling
     * Suspends the current coroutine until the detailed address is resolved
     * @param context Application context
     * @param location LatLng coordinates to get detailed address for
     * @return DetailedAddress object or null if address cannot be determined
     * @see getDetailedAddressFromLocation
     */
    suspend fun getDetailedAddressFromLocationAsync(
        context: Context,
        location: LatLng
    ): DetailedAddress? = suspendCancellableCoroutine { continuation ->
        getDetailedAddressFromLocation(context, location) { detailedAddress ->
            continuation.resume(detailedAddress)
        }
    }

}

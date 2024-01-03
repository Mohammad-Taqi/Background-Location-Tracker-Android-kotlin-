package com.example.backgroundlocationtracker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

/**
 * Default implementation of the [LocationClient] interface, providing location updates using the
 * [FusedLocationProviderClient].
 *
 * @param context The application context required for various location-related operations.
 * @param client The [FusedLocationProviderClient] responsible for handling location requests.
 */
class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
) :
    LocationClient {

    /**
     * Retrieves location updates at a specified interval using a [Flow].
     *
     * @param interval The time interval, in milliseconds, at which location updates should be received.
     * @return A [Flow] emitting [Location] updates.
     * @throws LocationException Thrown if location permissions are missing or if GPS is not enabled.
     */
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            // Check if the app has the required location permissions
            if (!context.hasLocationPermission()) {
                throw LocationClient.LocationException("Missing Location Permissions")
            }

            // Obtain the system location manager to check the status of GPS and network providers
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            // Throw an exception if both GPS and network providers are disabled
            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationClient.LocationException("GPS is not enabled!")
            }

            // Create a LocationRequest for location updates
//    @Deprecated
//            val request =
//                LocationRequest.create().setInterval(interval).setFastestInterval(interval)
            val request2 =
                LocationRequest.Builder(interval).build()

            // Define a callback to handle location updates and send them through the Flow
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult.locations.lastOrNull()?.let {
                        // Use coroutine launch to send the location result asynchronously
                        launch { send(it) }
                    }
                }
            }

            // Request location updates using the FusedLocationProviderClient
            client.requestLocationUpdates(request2, locationCallback, Looper.getMainLooper())

            // Close the Flow and remove location updates when it's cancelled
            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
}

package com.example.backgroundlocationtracker

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a location client that provides methods to retrieve location updates.
 */
interface LocationClient {

    /**
     * Gets location updates at a specified interval using a Kotlin Flow.
     *
     * @param interval The time interval, in milliseconds, at which location updates should be received.
     * @return A [Flow] emitting [Location] updates.
     */
    fun getLocationUpdates(interval: Long): Flow<Location>

    /**
     * Custom exception class for location-related errors.
     *
     * @param message The detailed error message associated with the exception.
     */
    class LocationException(message: String) : Exception()
}

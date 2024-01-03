package com.example.backgroundlocationtracker

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val CHANNEL_ID = "location"

/**
 * Service responsible for tracking the device's location in the background using a [LocationClient].
 */
class LocationService : Service() {

    // Coroutine scope for handling background tasks
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Location client for obtaining location updates
    private lateinit var locationClient: LocationClient

    // onBind is not used, returning null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Called when the service is created
    override fun onCreate() {
        super.onCreate()

        // Initialize the location client with the default implementation
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    // Called when the service is started
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check the action provided in the intent and perform corresponding actions
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }

        // Return the default behavior of the onStartCommand
        return super.onStartCommand(intent, flags, startId)
    }

    // Called when the service is destroyed
    override fun onDestroy() {
        super.onDestroy()

        // Cancel the service scope to clean up any ongoing tasks
        serviceScope.cancel()
    }

    // Start tracking the location in the background
    private fun start() {
        // Create a notification for the foreground service
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tracking...")
            .setContentText("Location : null")
            .setSmallIcon(R.drawable.ic_launcher_background).setOnlyAlertOnce(true)
            .setOngoing(true)//If your app shows non-dismissable foreground notifications to users, Android 14 has changed the behavior to allow users to dismiss such notifications.

        // Get the notification manager
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Obtain location updates using the location client
        locationClient.getLocationUpdates(10000L)
            .catch { }
            .onEach {
                // Update the notification content with the current location
                val lat = it.latitude
                val long = it.longitude
                val updatedNotification = notification.setContentText("Location : ($lat,$long)")
                Log.d("CheckingLocation", "start: $lat , $long")

                // Notify the user about the updated location
                notificationManager.notify(12, updatedNotification.build())
            }.launchIn(serviceScope)

        // Start the service in the foreground, showing the ongoing notification
        notificationManager.notify(12, notification.build())
        startForeground(12, notification.build())
    }

    // Stop the background tracking service
    private fun stop() {
//        @Deprecated
//        stopForeground(true)
        stopForeground(STOP_FOREGROUND_REMOVE)
        // Stop the service
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}

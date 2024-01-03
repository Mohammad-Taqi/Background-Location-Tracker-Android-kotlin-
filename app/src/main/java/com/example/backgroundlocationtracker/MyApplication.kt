package com.example.backgroundlocationtracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * Custom application class for initializing application-wide settings and resources.
 */
class MyApplication : Application() {

    // Called when the application is created
    override fun onCreate() {
        super.onCreate()

        // Check if the Android version is Oreo or newer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel for devices running Oreo or newer
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )

            // Get the notification manager service
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Create the notification channel
            notificationManager.createNotificationChannel(channel)
        }
    }
}

package com.example.backgroundlocationtracker

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat

/**
 * The main activity of the application responsible for user interaction.
 */
class MainActivity : AppCompatActivity() {

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for the activity
        setContentView(R.layout.activity_main)

        // Request location permissions when the activity is created
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 0
        )
    }

    /**
     * Callback method for the "Start" button click.
     *
     * @param view The view that triggered the click event.
     */
    fun start(view: View) {
        // Create an intent to start the LocationService with the ACTION_START action
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            // Start the service using the created intent
            startService(this)
        }
    }

    /**
     * Callback method for the "Stop" button click.
     *
     * @param view The view that triggered the click event.
     */
    fun stop(view: View) {
        // Create an intent to start the LocationService with the ACTION_STOP action
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            // Start the service using the created intent
            startService(this)
        }
    }
}

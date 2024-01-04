# Background Location Tracker 👋

Background Location Tracker is a demonstration Android application that showcases how to track the device's location in the background using the Fused Location Provider API. The app utilizes a foreground service to ensure location updates even when the app is in the background.

## Features

- Track location updates in the background.
- Start and stop tracking using the provided buttons.
- Display real-time location updates in a persistent notification.

## Getting Started

### Prerequisites

- Android Studio
- Android device (with developer options enabled) or emulator
- Google Play services installed on the device/emulator

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/background-location-tracker.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on your Android device or emulator.

### Permissions

The app requires the following permissions, which are requested at runtime:

- `INTERNET`: Used for network-related operations.
- `ACCESS_COARSE_LOCATION`: Used to obtain approximate device location.
- `ACCESS_FINE_LOCATION`: Used to obtain more precise device location.
- `POST_NOTIFICATIONS`: Required for posting notifications.
- `FOREGROUND_SERVICE`: Required for running services in the foreground.
- `FOREGROUND_SERVICE_LOCATION`: Required for the foreground service with a location type.

### Manifest Configuration

Ensure that the following permissions and service declaration are present in your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />

<application>
    <!-- ... -->

    <!-- Register the LocationService in the manifest with foregroundServiceType="location" -->
    <service
        android:name=".LocationService"
        android:foregroundServiceType="location" />
        
    <!-- ... -->
</application>
```

## Usage

1. Launch the app on your device.

2. Grant the required location permissions when prompted.

3. Use the "Start" button to initiate background location tracking.

4. Use the "Stop" button to halt location tracking.

5. Observe real-time location updates in the persistent notification.

## Implementation Details

- The app uses the Fused Location Provider API to receive location updates.
- A foreground service is employed to ensure continuous tracking even when the app is in the background.

## References

- [YouTube Tutorial](https://youtu.be/YZL-_XJSClc?si=YWrIiUvIyXZovvUM) - I Followed this tutorial to create this demo.

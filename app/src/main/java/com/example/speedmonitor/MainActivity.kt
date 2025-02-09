package com.example.speedmonitor

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.Manifest
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.speedmonitor.notifier.FirebaseNotifier
import com.example.speedmonitor.service.SpeedMonitorService
import com.example.speedmonitor.utils.TAG
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


class MainActivity : AppCompatActivity() {

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "Notification Permission granted")
        } else {
            Log.d(TAG, "Notification Permission not granted")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "App started, speed monitoring initialized")

//        Firebase.messaging.subscribeToTopic("speedmonitor")
//            .addOnCompleteListener { task ->
//                var msg = "Subscribed"
//                if (!task.isSuccessful) {
//                    msg = "Subscribe failed"
//                }
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            }
        val notifier = FirebaseNotifier(this)
        notifier.getFCMToken("car123")
        val speedMonitor = SpeedMonitorService(notifier)

        speedMonitor.setSpeedLimit("RenterId", 100)
        speedMonitor.checkSpeed("car123", 110) // Simulate car speeding

    }
  fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Log.d("Notification", "Permission already granted")
        }
    }
}
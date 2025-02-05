package com.example.speedmonitor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.speedmonitor.notifier.FirebaseNotifier
import com.example.speedmonitor.service.SpeedMonitorService
import com.example.speedmonitor.utils.TAG

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notifier = FirebaseNotifier()
        val speedMonitor = SpeedMonitorService(notifier)

        speedMonitor.setSpeedLimit("RenterId", 100)
        speedMonitor.checkSpeed("car123", 10) // Simulate car speeding

        Log.d(TAG, "App started, speed monitoring initialized")
    }
}
package com.example.speedmonitor.service

import android.util.Log
import com.example.speedmonitor.notifier.FirebaseNotifier
import com.example.speedmonitor.utils.TAG

class SpeedMonitorService(private val notifier: FirebaseNotifier) : SpeedMonitor {
    private val rentersSpeedLimits = mutableMapOf<String, Int>()

    fun setSpeedLimit(renterId: String, speedLimit: Int) {
        rentersSpeedLimits[renterId] = speedLimit
        Log.d(TAG, "Speed limit set for renter $renterId: $speedLimit km/h")
//        Need to implement API calls to set
    }

    override fun checkSpeed(carId: String, speed: Int) {
        val renterId = getRenterIdForCar(carId) ?: return
        val maxSpeed = rentersSpeedLimits[renterId] ?: return

        if (speed > maxSpeed) {
            Log.d(TAG, "Speed exceeded.Notifying rental company.")
            notifier.notifyRentalCompany(carId, speed)
            Log.d(TAG, "Speed exceeded.Notifying driver.")
            notifier.alertDriver()
        }
    }

    private fun getRenterIdForCar(carId: String): String {
        // fetch actual renter ID from a data source
        return "RenterId"
    }
}
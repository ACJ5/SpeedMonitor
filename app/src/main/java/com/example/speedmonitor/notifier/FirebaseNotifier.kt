package com.example.speedmonitor.notifier

import android.util.Log
import com.example.speedmonitor.utils.TAG

class FirebaseNotifier : Notifier {
    override fun notifyRentalCompany(carId: String, speed: Int) {
        Log.d(TAG,"Alert: Car $carId exceeded speed limit ($speed km/h)")

    }

    override fun alertDriver() {
        Log.d(TAG, "Driver Alert: Reduce speed!")
    }

}
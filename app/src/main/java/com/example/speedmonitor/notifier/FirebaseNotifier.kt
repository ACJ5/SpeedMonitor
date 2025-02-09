package com.example.speedmonitor.notifier

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.speedmonitor.MainActivity
import com.example.speedmonitor.utils.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.example.speedmonitor.R


class FirebaseNotifier(private val context: Context) : Notifier {

    private val CHANNEL_ID = "speed_monitor_channel"
    private val NOTIFICATION_ID_CAR = 1001
    private val NOTIFICATION_ID_RENTER = 1002


    init {
        createNotificationChannel()
    }

    override fun notifyRentalCompany(carId: String, speed: Int) {
        Log.d(TAG,"Alert: Car $carId exceeded speed limit ($speed km/h)")
        showNotification("Alert for rental company", "Car $carId exceeded speed limit ($speed km/h)", NOTIFICATION_ID_RENTER)
//        As per the communication from the app firebase should notify rental company with carId and speed.
    }

    override fun alertDriver() {
        Log.d(TAG, "Driver Alert: Reduce speed!")
        showNotification("Alert for Driver", "Car exceeded speed limit", NOTIFICATION_ID_CAR)
//        Car driver should be notified from firebase according to the speed limit.
    }

    fun getFCMToken(carId: String){

        //This function is to fetch token for each car
        Log.d(TAG, "Get new FCM registration token")

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }else{
//                Log.d(TAG, "Fetching FCM registration token successful")
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//            Log.d(TAG, "FCM Token: $token")
//
//            // Log and toast
//            val msg = context.getString(R.string.msg_token_fmt, token)
//            Log.d(TAG, msg)
//
//            sendTokenToBackend(token, carId)
//        })
    }

    private fun sendTokenToBackend(token: String?, carId: String) {
        Log.d(TAG, "Sending FCM Token to Backend: $token, CarID: $carId")
        // Need to implement API call to register this token in the database along with car ID
    }

    private fun showNotification(title: String, message: String,notificationId: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (context is MainActivity) {
                context.requestNotificationPermission()
            }
            return
        }
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Rental Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for rental alerts"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
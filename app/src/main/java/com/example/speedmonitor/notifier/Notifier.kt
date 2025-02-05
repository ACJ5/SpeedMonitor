package com.example.speedmonitor.notifier

interface Notifier {
    fun notifyRentalCompany(carId: String, speed: Int)
    fun alertDriver()
}
package com.example.speedmonitor.service

interface SpeedMonitor {
    fun checkSpeed(carId: String, speed: Int)
}
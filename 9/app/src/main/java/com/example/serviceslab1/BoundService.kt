package com.example.serviceslab1


import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoundService : Service() {

    private val myBinder: IBinder = MyLocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }

    fun  getCurrentTime() : String {
    val dataFormat= SimpleDateFormat("HH:mm:ss mm/dd/yyyy", Locale.US)
    return dataFormat.format(Date())
    }

   inner class MyLocalBinder : Binder() {
        fun getService() : BoundService {
            return this@BoundService
        }
    }
}

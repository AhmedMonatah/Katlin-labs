package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ImageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val path = intent?.getStringExtra("path")
        ImageActivity.imagePath.value = path
        Toast.makeText(context, "Image downloaded: $path", Toast.LENGTH_SHORT).show()
    }
}
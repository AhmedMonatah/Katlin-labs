package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.io.File


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class ImageActivity : ComponentActivity() {

    companion object {
        val imagePath: MutableState<String?> = mutableStateOf(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            ImageScreen(imagePath.value)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val pathFromIntent = intent?.getStringExtra("path")
        if (pathFromIntent != null) {
            imagePath.value = pathFromIntent
        } else if (imagePath.value == null) {
            val file = File(cacheDir, "image-service.jpg")
            if (file.exists()) {
                imagePath.value = file.absolutePath
            }
        }
    }
}

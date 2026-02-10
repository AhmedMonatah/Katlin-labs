package com.example.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File

import java.net.URL

class ProductsWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            val json = URL("https://dummyjson.com/products").readText()
            val file = File(applicationContext.filesDir, "products.json")
            file.writeText(json)
            Result.success(workDataOf("file_name" to file.name))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

package com.example.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.ui.theme.WorkManagerTheme
import org.json.JSONObject
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsScreen()
        }
    }
}

@Composable
fun ProductsScreen() {

    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)

    val workRequest = remember {
        OneTimeWorkRequestBuilder<ProductsWorker>().build()
    }

    workManager.enqueue(workRequest)
    val workInfo = workManager.getWorkInfoByIdLiveData(workRequest.id).observeAsState()

    when (workInfo.value?.state) {

        WorkInfo.State.RUNNING -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        WorkInfo.State.SUCCEEDED -> {
            val fileName = workInfo.value?.outputData?.getString("file_name") ?: ""
            val file = File(LocalContext.current.filesDir, fileName)

            val json = if (file.exists()) file.readText() else ""

            val products = remember(json) { parseProducts(json) }

            LazyColumn {
                items(products) { product ->
                    ProductItem(product)
                }
            }
        }

        WorkInfo.State.FAILED -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
                Text("Failed to load products")
            }
        }

        else -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
                Text("Waiting...")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagerTheme {
        ProductsScreen()
    }
}


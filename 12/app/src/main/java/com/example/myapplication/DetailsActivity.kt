package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.Product
import com.example.myapplication.theme.MyApplicationTheme
import com.example.myapplication.ui.ProductDetailsContent
@RequiresApi(Build.VERSION_CODES.TIRAMISU)

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val product = intent.getParcelableExtra("PRODUCT", Product::class.java)

        setContent {
            MyApplicationTheme {
                Column(Modifier.fillMaxSize()) {
                    product?.let {
                        ProductDetailsContent(it)
                    }
                }
            }
        }
    }
}

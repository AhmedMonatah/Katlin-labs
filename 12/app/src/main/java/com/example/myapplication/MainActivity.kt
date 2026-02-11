package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Product
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.ProductApi
import com.example.myapplication.data.RetrofitHelper
import com.example.myapplication.ui.ProductDetailsContent
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(this)
        val dao = db.productDao()
        val api = RetrofitHelper.productApi
        lifecycleScope.launch {
            try {
                val response = api.getProducts()
                dao.insertAll(response.products)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            val products by dao.getAllProducts().collectAsState(initial = emptyList())
            var selectedProduct by remember { mutableStateOf<Product?>(null) }

            val conf = LocalConfiguration.current
            val isLandscape = conf.orientation == Configuration.ORIENTATION_LANDSCAPE

            if (isLandscape) {
                Row(Modifier.fillMaxSize()) {
                    Box(Modifier.weight(1f)) {
                        ProductList(products) { selectedProduct = it }
                    }
                    Box(Modifier.weight(1f)) {
                        selectedProduct?.let { ProductDetailsContent(it) } ?: Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Select a product")
                        }
                    }
                }
            } else {
                ProductList(products){ product ->
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtra("PRODUCT", product)
                    startActivity(intent)
                }
            }
        }

    }
}

@Composable
fun ProductList(products: List<Product>, modifier: Modifier = Modifier, onItemClick: (Product) -> Unit) {
    LazyColumn(modifier.fillMaxSize()) {
        items(products) { product ->
            ProductItem(product) { onItemClick(product) }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(Modifier.padding(16.dp).fillMaxWidth()) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(64.dp).padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Text(product.title, style = MaterialTheme.typography.titleLarge)
        }
    }
}


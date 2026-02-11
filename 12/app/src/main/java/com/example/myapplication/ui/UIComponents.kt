package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.Product

@Composable
fun ProductDetailsContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(16.dp))
        Text(product.title, style = MaterialTheme.typography.headlineMedium)
        Text("Category: ${product.category}", style = MaterialTheme.typography.bodyLarge)
        Text("Price: $${product.price}", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        Text(product.description, style = MaterialTheme.typography.bodyMedium)
    }
}

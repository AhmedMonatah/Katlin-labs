package com.example.workmanager


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductItem(product: Product) {
    Column(modifier = Modifier.fillMaxWidth().padding(35.dp)) {
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text(product.title, fontWeight = FontWeight.Bold)
        Text("$${product.price}")
    }
}

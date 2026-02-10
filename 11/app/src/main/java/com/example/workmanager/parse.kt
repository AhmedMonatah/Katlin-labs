package com.example.workmanager

import org.json.JSONObject

fun parseProducts(json: String): List<Product> {
    val products = mutableListOf<Product>()
    val root = JSONObject(json)
    val array = root.getJSONArray("products")

    for (i in 0 until array.length()) {
        val item = array.getJSONObject(i)
        products.add(
            Product(
                id = item.getInt("id"),
                title = item.getString("title"),
                price = item.getInt("price")
            )
        )
    }
    return products
}

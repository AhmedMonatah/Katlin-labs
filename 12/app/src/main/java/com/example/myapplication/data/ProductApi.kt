package com.example.myapplication.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product>
)

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}

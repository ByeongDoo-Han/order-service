package com.example.sajeon.domain

data class Order(
    val id: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val totalPrice: Double
)

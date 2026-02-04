package com.example.sajeon.domain

import com.example.sajeon.dto.ProductRequest

data class Product(
    val id: Long,
    var name: String,
    var seller: String,
    var price: Double,
    var stock: Int,
    var description: String,
    var active: Active = Active.ACTIVE
) {
    private fun canOrder(): Boolean {
        return stock > 0
    }

    fun order(quantity: Int) {
        if (active != Active.ACTIVE) {
            throw IllegalStateException("Product is not active.")
        }
        if (!canOrder()) {
            throw IllegalStateException("Product is out of stock.")
        }
        if (stock < quantity) {
            throw IllegalArgumentException("Insufficient stock for the order.")
        }
        // Reduce stock
        val newStock = stock - quantity
        // In a real application, you would update the stock in the database here
    }

    fun deactivate() :Product{
        return copy(
            active = Active.INACTIVE
        )
    }

    fun update(
        name: String?,
        price: Double?,
        stock: Int?,
        description: String?,
        seller: String?,
        active: Active?
    ): Product {
        return copy(
            name = name ?: this.name,
            price = price ?: this.price,
            stock = stock ?: this.stock,
            description = description ?: this.description,
            seller = seller ?: this.seller,
            active = active ?: this.active
        )
    }
}

enum class Active {
    ACTIVE,
    INACTIVE
}
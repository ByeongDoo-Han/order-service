package com.example.sajeon.entity

import com.example.sajeon.domain.Order
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class OrderEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    val product:ProductEntity,
    val quantity: Int,
    val totalPrice: Double
) {
    fun toDomain(): Order {
        return Order(
            id = this.id!!,
            productId = this.product.id!!,
            productName = this.product.name,
            quantity = this.quantity,
            totalPrice = this.totalPrice
        )
    }
}
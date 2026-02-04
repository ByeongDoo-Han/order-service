package com.example.sajeon.entity

import com.example.sajeon.domain.Active
import com.example.sajeon.domain.Product
import com.example.sajeon.dto.ProductRequest
import jakarta.persistence.*

@Entity
class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var price: Double,
    var stock: Int,
    var description: String,
    var seller: String,
    @Enumerated(EnumType.STRING)
    var active: Active
){
    fun toDomain(): Product {
        return Product(
            id = this.id!!,
            name = this.name,
            price = this.price,
            stock = this.stock,
            description = this.description,
            seller = this.seller,
            active = this.active
        )
    }

    fun update(name: String?, price: Double?, stock: Int?, description: String?, seller: String?, active: Active?) {
        if (name != null) this.name = name
        if (price != null) this.price = price
        if (stock != null) this.stock = stock
        if (description != null) this.description = description
        if (seller != null) this.seller = seller
        if (active != null) this.active = active
    }

    fun deactivate() {
        this.active = Active.INACTIVE
    }

    companion object{
        fun fromDomain(product: Product): ProductEntity {
            return ProductEntity(
                id = product.id,
                name = product.name,
                price = product.price,
                stock = product.stock,
                description = product.description,
                seller = product.seller,
                active = product.active
            )
        }
    }
}
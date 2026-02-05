package com.example.sajeon.service

import com.example.sajeon.domain.Active
import com.example.sajeon.domain.Order
import com.example.sajeon.domain.Product
import com.example.sajeon.dto.CursorPage
import com.example.sajeon.dto.OrderRequest
import com.example.sajeon.entity.OrderEntity
import com.example.sajeon.entity.ProductEntity
import com.example.sajeon.repository.OrderRepository
import com.example.sajeon.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {
    @Transactional
    fun createOrder(orderRequest: OrderRequest): Order {
        val id = orderRequest.productId
        val quantity = orderRequest.quantity
        val productEntity = productRepository.findById(id).orElseThrow {
            throw NoSuchElementException("Product with id $id not found")
        }
        if(productEntity.active != Active.ACTIVE){
            throw IllegalStateException("Product with id $id is not active")
        }
        if (productEntity.stock < 0) {
            throw IllegalArgumentException("Insufficient stock for product with id $id")
        }

        val product = productEntity.toDomain().order(quantity)
        val newProductEntity = productRepository.save(ProductEntity.fromDomain(product))

        val orderEntity = orderRepository.save(
            OrderEntity(
                product = newProductEntity,
                quantity = quantity,
                totalPrice = newProductEntity.price * quantity
            )
        )
        return orderEntity.toDomain()
    }

    fun getOrder(id: Long): Order {
        val orderEntity = orderRepository.findById(id).orElseThrow {
            throw NoSuchElementException("Order with id $id not found")
        }
        return orderEntity.toDomain()
    }

    fun getOrdersByCursor(cursor: Long?, size: Int, keyword: String?): CursorPage<Order> {
        val orders = orderRepository.findNextPage(cursor, size, keyword)
            .take(size+1)
        val hasNext = orders.size > size
        val content = if (hasNext) orders.dropLast(1) else orders
        val nextCursor = content.lastOrNull()?.id
        return CursorPage(
            content = content.map {
                Order(
                    id = it.id!!,
                    productId = it.product.id!!,
                    productName = it.product.name,
                    quantity = it.quantity,
                    totalPrice = it.totalPrice
                )
            },
            nextCursor = if (hasNext) nextCursor else null,
            hasNext = hasNext
        )
    }
}

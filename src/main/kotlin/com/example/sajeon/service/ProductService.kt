package com.example.sajeon.service

import com.example.sajeon.domain.Active
import com.example.sajeon.domain.Product
import com.example.sajeon.dto.CursorPage
import com.example.sajeon.dto.ProductRequest
import com.example.sajeon.entity.ProductEntity
import com.example.sajeon.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    @Transactional
    fun createProduct(productRequest: ProductRequest): Product {
        val productEntity = ProductEntity(
            name = productRequest.name!!,
            price = productRequest.price!!,
            stock = productRequest.stock!!,
            description = productRequest.description!!,
            seller = productRequest.seller!!,
            active = productRequest.active!!
        )
        return productRepository.save(productEntity).toDomain()
    }

    fun getProductsByCursor(cursor: Long?, size: Int, keyword: String?): CursorPage<Product> {
        val products = productRepository.findNextPage(cursor, size, keyword)
            .take(size+1)
        val hasNext = products.size > size
        val content = if (hasNext) products.dropLast(1) else products
        val nextCursor = content.lastOrNull()?.id
        return CursorPage(
            content = content.map {
                Product(
                    id = it.id!!,
                    name = it.name,
                    price = it.price,
                    stock = it.stock,
                    description = it.description,
                    seller = it.seller,
                    active = it.active
                )
            },
            nextCursor = if (hasNext) nextCursor else null,
            hasNext = hasNext
        )
    }

    fun getProductById(id: Long): Product {
        productRepository.findById(id).let {
            if (it.isEmpty) {
                throw NoSuchElementException("Product with id $id not found")
            }
            val productEntity = it.get()
            return Product(
                id = productEntity.id!!,
                name = productEntity.name,
                price = productEntity.price,
                stock = productEntity.stock,
                description = productEntity.description,
                seller = productEntity.seller,
                active = productEntity.active
            )
        }
    }

    @Transactional
    fun updateProduct(productRequest: ProductRequest, id: Long): Product {
        val productEntity = productRepository.findById(id).orElseThrow {
            NoSuchElementException("Product with id $id not found")
        }
        productEntity.update(productRequest.name, productRequest.price, productRequest.stock, productRequest.description, productRequest.seller, productRequest.active)
        return productEntity.toDomain()
    }

    @Transactional
    fun deleteProduct(id: Long): Product {
        val productEntity = productRepository.findById(id).orElseThrow {
            NoSuchElementException("Product with id $id not found")
        }
        productEntity.deactivate()
        return productEntity.toDomain()
    }
}

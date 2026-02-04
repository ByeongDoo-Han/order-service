package com.example.sajeon.controller

import com.example.sajeon.domain.Product
import com.example.sajeon.dto.CursorPage
import com.example.sajeon.dto.ProductRequest
import com.example.sajeon.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(@RequestBody productRequest: ProductRequest): Product {
        return productService.createProduct(productRequest)

    }

    @GetMapping
    fun getProducts(
        @RequestParam(required = false) cursor:Long?,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) keyword: String?
    ): CursorPage<Product> {
        return productService.getProductsByCursor(cursor, size, keyword)
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id:Long): Product {
        return productService.getProductById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id:Long): Product {
        return productService.deleteProduct(id)
    }

    @PostMapping("/{id}")
    fun updateProduct(
        @RequestBody productRequest:ProductRequest,
        @PathVariable id:Long
    ): Product {
        return productService.updateProduct(productRequest, id)
    }
}
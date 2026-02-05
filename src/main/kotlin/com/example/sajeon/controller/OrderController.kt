package com.example.sajeon.controller

import com.example.sajeon.domain.Order
import com.example.sajeon.domain.Product
import com.example.sajeon.dto.CursorPage
import com.example.sajeon.dto.OrderRequest
import com.example.sajeon.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/orders")
@RestController
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    fun createOrder(
        @RequestBody orderRequest: OrderRequest
    ): Order {
        return orderService.createOrder(orderRequest)
    }

    @GetMapping("/{id}")
    fun getOrder(
        @PathVariable id: Long
    ): Order {
        return orderService.getOrder(id)
    }

    @GetMapping
    fun getOrders(
        @RequestParam(required = false) cursor:Long?,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) keyword: String?
    ): CursorPage<Order> {
        return orderService.getOrdersByCursor(cursor, size, keyword)
    }
}
package com.example.sajeon.dto

import com.example.sajeon.domain.Active
import com.example.sajeon.entity.ProductEntity

data class ProductRequest(
    val name: String?,
    val price: Double?,
    val stock: Int?,
    val description: String?,
    val seller: String?,
    val active: Active? = Active.ACTIVE
)
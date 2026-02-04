package com.example.sajeon.dto

data class CursorPage<T>(
    val content: List<T>,
    val hasNext: Boolean,
    val nextCursor: Long?
)

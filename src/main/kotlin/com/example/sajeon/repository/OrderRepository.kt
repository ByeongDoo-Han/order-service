package com.example.sajeon.repository

import com.example.sajeon.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long>{
    @Query(
        """
        SELECT o FROM OrderEntity o
        JOIN FETCH o.product p
        WHERE (:keyword IS NULL OR p.name LIKE %:keyword%)
        AND (:cursor IS NULL OR o.id < :cursor)
        ORDER BY o.id DESC
        """
    )
    fun findNextPage(cursor: Long?, size: Int, keyword: String?): List<OrderEntity>

}

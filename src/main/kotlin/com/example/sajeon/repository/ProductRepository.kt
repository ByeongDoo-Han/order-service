package com.example.sajeon.repository

import com.example.sajeon.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long>{
    @Query(
        """
        SELECT p FROM ProductEntity p
        WHERE (:keyword IS NULL OR p.name LIKE %:keyword%)
        AND (:cursor IS NULL OR p.id < :cursor)
        AND p.active = 'ACTIVE'
        ORDER BY p.id DESC
        """
    )
    fun findNextPage(cursor: Long?, size: Int, keyword: String?): List<ProductEntity>
}

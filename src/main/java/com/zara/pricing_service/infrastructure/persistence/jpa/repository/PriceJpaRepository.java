package com.zara.pricing_service.infrastructure.persistence.jpa.repository;

import com.zara.pricing_service.infrastructure.persistence.jpa.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p
            FROM PriceEntity p
            WHERE p.brandId = :brandId
              AND p.productId = :productId
              AND :applicationDate BETWEEN p.startDate AND p.endDate
            """)
    List<PriceEntity> findBestApplicablePrice(
            @Param("brandId") long brandId,
            @Param("productId") long productId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}

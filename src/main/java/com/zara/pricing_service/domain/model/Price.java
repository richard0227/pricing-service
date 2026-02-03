package com.zara.pricing_service.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record Price(
        long brandId,
        long productId,
        int priceList,
        int priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Money money
) {
    public Price {
        Objects.requireNonNull(startDate, "startDate");
        Objects.requireNonNull(endDate, "endDate");
        Objects.requireNonNull(money, "money");
    }
}

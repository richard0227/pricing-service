package com.zara.pricing_service.application.dto;

import com.zara.pricing_service.domain.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ApplicablePriceResponse(
        long productId,
        long brandId,
        int priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
    public static ApplicablePriceResponse from(Price price) {
        return new ApplicablePriceResponse(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.startDate(),
                price.endDate(),
                price.money().amount(),
                price.money().currency()
        );
    }
}

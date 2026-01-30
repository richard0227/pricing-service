package com.zara.pricing_service.infrastructure.persistence.jpa.mapper;

import com.zara.pricing_service.domain.model.Money;
import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.infrastructure.persistence.jpa.entity.PriceEntity;

public class PriceEntityMapper {
    public Price toDomain(PriceEntity e) {
        return new Price(
                e.getBrandId(),
                e.getProductId(),
                e.getPriceList(),
                e.getPriority(),
                e.getStartDate(),
                e.getEndDate(),
                new Money(e.getPrice(), e.getCurrency())
        );
    }
}

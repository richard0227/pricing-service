package com.zara.pricing_service.domain.port.out;

import com.zara.pricing_service.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceLookupPort {
    Optional<Price> findBestApplicablePrice(long brandId, long productId, LocalDateTime applicationDate);
}

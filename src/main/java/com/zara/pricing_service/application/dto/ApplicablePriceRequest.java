package com.zara.pricing_service.application.dto;

import java.time.LocalDateTime;

public record ApplicablePriceRequest(long brandId, long productId, LocalDateTime applicationDate) {
}


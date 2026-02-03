package com.zara.pricing_service.infrastructure.persistence.jpa.adapter;

import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.domain.port.out.PriceLookupPort;
import com.zara.pricing_service.infrastructure.persistence.jpa.mapper.PriceEntityMapper;
import com.zara.pricing_service.infrastructure.persistence.jpa.repository.PriceJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceJpaAdapter implements PriceLookupPort {

    private final PriceJpaRepository priceRepository;
    private final PriceEntityMapper priceMapper = new PriceEntityMapper();

    public PriceJpaAdapter(PriceJpaRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> findBestApplicablePrice(long brandId, long productId, LocalDateTime applicationDate) {
        Pageable firstPricePageable = PageRequest.of(0, 1);
        return priceRepository.findBestApplicablePrice(brandId, productId, applicationDate, firstPricePageable)
                .stream().findFirst()
                .map(priceMapper::toDomain);
    }
}

package com.zara.pricing_service.domain.service;

import com.zara.pricing_service.domain.error.PriceNotFoundException;
import com.zara.pricing_service.domain.model.Money;
import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.domain.port.out.PriceLookupPort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicablePriceSelectorTest {

    @Test
    void picks_highest_priority_when_overlaps() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price low = new Price(1, 35455, 1, 0,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new Money(new BigDecimal("35.50"), "EUR"));

        Price high = new Price(1, 35455, 2, 1,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                new Money(new BigDecimal("25.45"), "EUR"));

        PriceLookupPort port = (b, p, d) -> List.of(low, high);
        ApplicablePriceSelector selector = new ApplicablePriceSelector(port);

        Price best = selector.selectApplicablePrice(1, 35455, date);

        assertThat(best.priceList()).isEqualTo(2);
        assertThat(best.money().amount()).isEqualByComparingTo("25.45");
    }

    @Test
    void throws_when_no_applicable_price_found() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceLookupPort port = (b, p, d) -> List.of();
        ApplicablePriceSelector selector = new ApplicablePriceSelector(port);

        assertThatThrownBy(() -> selector.selectApplicablePrice(1, 35455, date))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("brandId=1")
                .hasMessageContaining("productId=35455")
                .hasMessageContaining(date.toString());
    }
}

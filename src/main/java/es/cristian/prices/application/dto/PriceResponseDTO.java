package es.cristian.prices.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponseDTO(Long productId, Long brandId, Integer priceList, LocalDateTime startDate,
		LocalDateTime endDate, BigDecimal price) {

}

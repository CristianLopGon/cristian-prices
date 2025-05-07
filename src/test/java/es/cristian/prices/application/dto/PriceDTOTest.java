package es.cristian.prices.application.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PriceDTOTest {

	LocalDateTime start = LocalDateTime.of(2025, 2, 14, 10, 0);
	LocalDateTime end = LocalDateTime.of(2025, 6, 15, 16, 0);

	@Test
	void priceDTOConstructorTest() {
		PriceResponseDTO dto = new PriceResponseDTO(35455L, 1L, 1, start, end, new BigDecimal(213));

		assertAll(() -> assertEquals(35455L, dto.productId()), () -> assertEquals(1L, dto.brandId()),
				() -> assertEquals(1, dto.priceList()));
	}

}

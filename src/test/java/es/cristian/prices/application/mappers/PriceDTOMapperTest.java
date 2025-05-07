package es.cristian.prices.application.mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.domain.models.Price;

@SpringBootTest
public class PriceDTOMapperTest {

	@Autowired
	private PriceDTOMapper mapper;

	LocalDateTime start = LocalDateTime.of(2025, 2, 14, 10, 0);
	LocalDateTime end = LocalDateTime.of(2025, 6, 15, 16, 0);
	PriceResponseDTO dto = new PriceResponseDTO(35455L, 1L, 1, start, end, new BigDecimal(213));

	Price price = new Price(35455L, 1L, start, end, 1, 1, new BigDecimal(213), "EUR");

	@Test
	void toDTOTest() {

		// when(mapper.toDto(price)).thenReturn(dto);
		PriceResponseDTO priceDto = mapper.toDto(price);

		assertAll(() -> assertNotNull(priceDto), () -> assertEquals(35455L, priceDto.productId()),
				() -> assertEquals(1L, priceDto.brandId()), () -> assertEquals(start, priceDto.startDate()));
	}

	@Test
	void toDomainTest() {
		// when(mapper.toDomain(dto)).thenReturn(price);
		Price priceResult = mapper.toDomain(dto);

		assertAll(() -> assertNotNull(priceResult), () -> assertEquals(35455L, priceResult.getProductId()),
				() -> assertEquals(1L, priceResult.getBrandId()),
				() -> assertEquals(start, priceResult.getStartDate()));
	}
}

package es.cristian.prices.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cristian.prices.domain.models.Price;
import es.cristian.prices.infrastructure.entities.PriceEntity;

@SpringBootTest
public class PriceEntityMapperTest {

	@Autowired
	private PriceEntityMapper mapper;

	LocalDateTime start = LocalDateTime.of(2025, 2, 14, 10, 0);
	LocalDateTime end = LocalDateTime.of(2025, 6, 15, 16, 0);
	Price price = new Price(35455L, 1L, start, end, 1, 1, new BigDecimal(213), "EUR");

	PriceEntity entity = new PriceEntity(1L, 35455L, 1L, start, end, 1, 1, new BigDecimal(213), "EUR");

	@Test
	void toEntityTest() {

		// when(mapper.toEntity(price)).thenReturn(entity);
		PriceEntity priceEntity = mapper.toEntity(price);

		assertNotNull(priceEntity);
		assertEquals(35455L, priceEntity.getProductId());
		assertEquals(1L, priceEntity.getBrandId());
		assertEquals(start, priceEntity.getStartDate());
	}

	@Test
	void toDomainTest() {
		// when(mapper.toDomain(entity)).thenReturn(price);
		Price priceResult = mapper.toDomain(entity);

		assertNotNull(priceResult);
		assertEquals(35455L, priceResult.getProductId());
		assertEquals(1L, priceResult.getBrandId());
		assertEquals(start, priceResult.getStartDate());
	}

}

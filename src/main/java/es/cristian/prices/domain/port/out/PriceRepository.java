package es.cristian.prices.domain.port.out;

import java.time.LocalDateTime;
import java.util.Optional;

import es.cristian.prices.domain.models.Price;

public interface PriceRepository {

	Optional<Price> findApplicablePrice(LocalDateTime date, Long productId, Long brandId);
}

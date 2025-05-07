package es.cristian.prices.application.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceRequestDTO(
		@NotNull(message = "date no puede ser nulo") @NotEmpty(message = "date no puede estar vacío") LocalDateTime date,
		@NotNull(message = "productId no puede ser nulo") @NotEmpty(message = "productId no puede estar vacío") @Positive(message = "productId debe ser positivo") Long productId,
		@NotNull(message = "brandId no puede ser nulo") @NotEmpty(message = "brandId no puede estar vacío") @Positive(message = "brandId debe ser positivo") Long brandId) {
}

package es.cristian.prices.application.mappers;

import org.mapstruct.Mapper;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.domain.models.Price;

@Mapper(componentModel = "spring")
public interface PriceDTOMapper {

	Price toDomain(PriceResponseDTO dto);

	PriceResponseDTO toDto(Price domain);
}

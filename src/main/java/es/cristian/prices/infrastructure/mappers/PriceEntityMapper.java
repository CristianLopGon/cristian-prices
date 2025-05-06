package es.cristian.prices.infrastructure.mappers;

import org.mapstruct.Mapper;

import es.cristian.prices.domain.models.Price;
import es.cristian.prices.infrastructure.entities.PriceEntity;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

	Price toDomain(PriceEntity entity);

	PriceEntity toEntity(Price domain);

}

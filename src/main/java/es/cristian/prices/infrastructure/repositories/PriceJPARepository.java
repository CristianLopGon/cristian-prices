package es.cristian.prices.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cristian.prices.infrastructure.entities.PriceEntity;

public interface PriceJPARepository extends JpaRepository<PriceEntity, Long> {

	@Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId "
			+ "AND :applicationDate BETWEEN p.startDate AND p.endDate " + "ORDER BY p.priority DESC")
	List<PriceEntity> findApplicablePrices(@Param("applicationDate") LocalDateTime applicationDate,
			@Param("productId") Long productId, @Param("brandId") Long brandId);
}

package es.cristian.prices.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {

	private Long productId;
	private Long brandId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer priceList;
	private Integer priority;
	private BigDecimal price;
	private String currency;

	public Price(Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList,
			Integer priority, BigDecimal price, String currency) {
		super();
		this.brandId = brandId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceList = priceList;
		this.productId = productId;
		this.priority = priority;
		this.price = price;
		this.currency = currency;
	}

	public Long getBrandId() {
		return brandId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public Long getProductId() {
		return productId;
	}

	public Integer getPriority() {
		return priority;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}
}

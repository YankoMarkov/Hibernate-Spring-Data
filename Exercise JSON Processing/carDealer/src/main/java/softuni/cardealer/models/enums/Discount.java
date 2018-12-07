package softuni.cardealer.models.enums;

import java.math.BigDecimal;

public enum Discount {
	
	ZERO_PERCENT(new BigDecimal("0")),
	FIVE_PERCENT(new BigDecimal("0.05")),
	TEN_PERCENT(new BigDecimal("0.1")),
	FIFTEEN_PERCENT(new BigDecimal("0.15")),
	TWENTY_PERCENT(new BigDecimal("0.2")),
	THIRTY_PERCENT(new BigDecimal("0.3")),
	FORTY_PERCENT(new BigDecimal("0.4")),
	FIFTY_PERCENT(new BigDecimal("0.5"));
	
	private BigDecimal discount;
	
	Discount(BigDecimal discount) {
		this.discount = discount;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}
}

package com.org.prod.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("product")
public class ProductServiceConfig {

	private String wasThenNowLabel;
	private String wasNowLabel;
	private String discountLabel;

	public String getWasThenNowLabel() {
		return wasThenNowLabel;
	}

	public void setWasThenNowLabel(String wasThenNowLabel) {
		this.wasThenNowLabel = wasThenNowLabel;
	}

	public String getWasNowLabel() {
		return wasNowLabel;
	}

	public void setWasNowLabel(String wasNowLabel) {
		this.wasNowLabel = wasNowLabel;
	}

	public String getDiscountLabel() {
		return discountLabel;
	}

	public void setDiscountLabel(String discountLabel) {
		this.discountLabel = discountLabel;
	}
}

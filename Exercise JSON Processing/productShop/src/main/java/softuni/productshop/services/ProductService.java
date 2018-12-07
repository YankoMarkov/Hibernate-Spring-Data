package softuni.productshop.services;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
	
	void seedProducts() throws IOException;
	
	void productsByRangeWithoutBuyer(BigDecimal min, BigDecimal max) throws IOException;
	
	void productSuccessfullySold() throws IOException;
}

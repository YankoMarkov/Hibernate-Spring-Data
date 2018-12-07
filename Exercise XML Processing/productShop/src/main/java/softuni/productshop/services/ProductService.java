package softuni.productshop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
	
	void seedProducts() throws IOException, JAXBException;
	
	void productsByRangeWithoutBuyer(BigDecimal min, BigDecimal max) throws IOException, JAXBException;
	
	void productSuccessfullySold() throws IOException, JAXBException;
}

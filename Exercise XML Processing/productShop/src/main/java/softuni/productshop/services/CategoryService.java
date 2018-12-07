package softuni.productshop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CategoryService {
	
	void seedCategory() throws IOException, JAXBException;
	
	void categoryByProductCount() throws IOException, JAXBException;
}

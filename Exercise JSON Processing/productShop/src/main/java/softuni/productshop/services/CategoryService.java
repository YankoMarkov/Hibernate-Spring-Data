package softuni.productshop.services;

import java.io.IOException;

public interface CategoryService {
	
	void seedCategory() throws IOException;
	
	void categoryByProductCount() throws IOException;
}

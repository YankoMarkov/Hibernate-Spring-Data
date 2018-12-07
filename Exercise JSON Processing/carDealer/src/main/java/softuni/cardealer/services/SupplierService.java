package softuni.cardealer.services;

import java.io.IOException;

public interface SupplierService {
	
	void seedSupplier() throws IOException;
	
	void getLocalSuppliers() throws IOException;
}

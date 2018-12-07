package softuni.cardealer.services;

import java.io.IOException;

public interface CustomerService {
	
	void seedCustomer() throws IOException;
	
	void getOrderedCustomers() throws IOException;
}

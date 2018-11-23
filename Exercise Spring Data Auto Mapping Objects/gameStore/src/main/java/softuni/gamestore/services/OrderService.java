package softuni.gamestore.services;

public interface OrderService {
	
	String addProduct(String title);
	
	String removeProduct(String title);
	
	String addProductsToUser();
}

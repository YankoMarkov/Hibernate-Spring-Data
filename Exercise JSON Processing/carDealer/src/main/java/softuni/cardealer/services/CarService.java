package softuni.cardealer.services;

import java.io.IOException;

public interface CarService {
	
	void seedCar() throws IOException;
	
	void getToyotaCars(String make) throws IOException;
	
	void getCarsWithParts() throws IOException;
}

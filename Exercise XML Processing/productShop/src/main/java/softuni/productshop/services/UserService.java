package softuni.productshop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface UserService {
	
	void seedUsers() throws IOException, JAXBException;
	
	void usersAndProducts() throws IOException, JAXBException;
}

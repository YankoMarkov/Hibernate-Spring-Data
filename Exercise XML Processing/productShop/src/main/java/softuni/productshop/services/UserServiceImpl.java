package softuni.productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.UserCreateDto;
import softuni.productshop.models.dtos.binding.UserImportDto;
import softuni.productshop.models.dtos.view.ProductDto;
import softuni.productshop.models.dtos.view.SoldProductsDto;
import softuni.productshop.models.dtos.view.UserCountDto;
import softuni.productshop.models.dtos.view.UserDto;
import softuni.productshop.models.entities.Product;
import softuni.productshop.models.entities.User;
import softuni.productshop.repositories.UserRepository;
import softuni.productshop.util.ValidatorUtil;
import softuni.productshop.util.XMLParser;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final String USER_XML_FILE_PATH = "src/main/resources/input/users.xml";
	private final String USER_AND_PRODUCT_FILE_PATH = "src/main/resources/output/users-and-products.xml";
	private final UserRepository userRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final XMLParser xmlParser;
	
	@Autowired
	public UserServiceImpl(XMLParser xmlParser, UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
		this.xmlParser = xmlParser;
		this.userRepository = userRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public void seedUsers() throws IOException, JAXBException {
		UserCreateDto userCreateDto = this.xmlParser.readXML(UserCreateDto.class, this.USER_XML_FILE_PATH);
		
		for (UserImportDto userImportDto : userCreateDto.getUserImportDtos()) {
			if (!this.validatorUtil.isValid(userImportDto)) {
				for (ConstraintViolation<UserImportDto> violation : this.validatorUtil.violations(userImportDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			User user = this.modelMapper.map(userImportDto, User.class);
			boolean exist = this.userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName());
			if (!exist) {
				this.userRepository.saveAndFlush(user);
			}
		}
	}
	
	@Override
	public void usersAndProducts() throws JAXBException {
		List<User> users = this.userRepository.findAll();
		UserCountDto userCountDto = new UserCountDto();
		userCountDto.setUsersCount(users.size());
		List<UserDto> userDtos = new ArrayList<>();
		
		for (User user : users) {
			UserDto userDto = new UserDto();
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setAge(user.getAge());
			SoldProductsDto soldProductsDto = new SoldProductsDto();
			soldProductsDto.setCount(user.getSold().size());
			List<ProductDto> productDtos = new ArrayList<>();
			for (Product product : user.getSold()) {
				ProductDto productDto = new ProductDto();
				productDto.setName(product.getName());
				productDto.setPrice(product.getPrice());
				productDtos.add(productDto);
			}
			soldProductsDto.setProductDtos(productDtos);
			userDto.setSoldProductsDto(soldProductsDto);
			userDtos.add(userDto);
		}
		userCountDto.setUserDtos(userDtos);
		this.xmlParser.writeXML(userCountDto, UserCountDto.class, this.USER_AND_PRODUCT_FILE_PATH);
	}
}

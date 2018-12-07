package softuni.productshop.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.UserCreateDto;
import softuni.productshop.models.dtos.view.ProductDto;
import softuni.productshop.models.dtos.view.SoldProductsDto;
import softuni.productshop.models.dtos.view.UserCountDto;
import softuni.productshop.models.dtos.view.UserDto;
import softuni.productshop.models.entities.Product;
import softuni.productshop.models.entities.User;
import softuni.productshop.repositories.UserRepository;
import softuni.productshop.util.FileUtil;
import softuni.productshop.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public UserServiceImpl(FileUtil file, UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, Gson gson) {
		this.file = file;
		this.userRepository = userRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/users.json");
	}
	
	@Override
	public void seedUsers() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		List<UserCreateDto> userCreateDtos = Arrays.asList(this.gson.fromJson(jsonString, UserCreateDto[].class));
		
		for (UserCreateDto userCreateDto : userCreateDtos) {
			if (!this.validatorUtil.isValid(userCreateDto)) {
				for (ConstraintViolation<UserCreateDto> violation : this.validatorUtil.violations(userCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			User user = this.modelMapper.map(userCreateDto, User.class);
			boolean exist = this.userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName());
			if (!exist) {
				this.userRepository.saveAndFlush(user);
			}
		}
	}
	
	@Override
	public void usersAndProducts() throws IOException {
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
		File writeFilePath = new File("src/main/resources/output/users-and-products.json");
		String jsonString = this.gson.toJson(userCountDto);
		this.file.writeFile(jsonString, writeFilePath);
	}
}

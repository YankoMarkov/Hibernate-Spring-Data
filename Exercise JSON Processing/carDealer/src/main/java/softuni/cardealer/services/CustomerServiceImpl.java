package softuni.cardealer.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.models.dtos.bindings.CustomerCreateDto;
import softuni.cardealer.models.dtos.views.OrderedCutomerDto;
import softuni.cardealer.models.entities.Customer;
import softuni.cardealer.repositories.CustomerRepository;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.customerRepository = customerRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/customers.json");
	}
	
	@Override
	public void seedCustomer() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		CustomerCreateDto[] customerCreateDtos = this.gson.fromJson(jsonString, CustomerCreateDto[].class);
		
		for (CustomerCreateDto customerCreateDto : customerCreateDtos) {
			if (!this.validatorUtil.isValid(customerCreateDto)) {
				for (ConstraintViolation<CustomerCreateDto> violation : this.validatorUtil.violations(customerCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Customer customer = this.modelMapper.map(customerCreateDto, Customer.class);
			LocalDateTime date = LocalDateTime.parse(customerCreateDto.getBirthDate());
			customer.setBirthDate(date.toLocalDate());
			boolean exist = this.customerRepository.existsByName(customer.getName());
			if (!exist) {
				this.customerRepository.saveAndFlush(customer);
			}
		}
	}
	
	@Override
	public void getOrderedCustomers() throws IOException {
		List<Customer> customers = this.customerRepository.findAllByOrderByBirthDate();
		customers.stream()
				.sorted((a, b) -> {
					int resulet = 0;
					if (a.getBirthDate().equals(b.getBirthDate())) {
						if (a.isYoungDriver() && b.isYoungDriver()) {
							resulet = 0;
						}
						if (!a.isYoungDriver() && !b.isYoungDriver()) {
							resulet = 0;
						}
						if (a.isYoungDriver() && !b.isYoungDriver()) {
							resulet = 1;
						}
					}
					return resulet;
				})
				.collect(Collectors.toList());
		
		List<OrderedCutomerDto> orderedCutomerDtos = new ArrayList<>();
		
		for (Customer customer : customers) {
			OrderedCutomerDto orderedCutomerDto = this.modelMapper.map(customer, OrderedCutomerDto.class);
			orderedCutomerDtos.add(orderedCutomerDto);
		}
		File writeFilePath = new File("src/main/resources/output/ordered-customers.json");
		String jsonString = this.gson.toJson(orderedCutomerDtos);
		this.file.writeFile(jsonString, writeFilePath);
	}
}

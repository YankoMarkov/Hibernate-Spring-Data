package softuni.cardealer.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.models.entities.Car;
import softuni.cardealer.models.entities.Customer;
import softuni.cardealer.models.entities.Sale;
import softuni.cardealer.models.enums.Discount;
import softuni.cardealer.repositories.CarRepository;
import softuni.cardealer.repositories.CustomerRepository;
import softuni.cardealer.repositories.SaleRepository;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.ValidatorUtil;

import java.io.File;
import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {
	private final SaleRepository saleRepository;
	private final CarRepository carRepository;
	private final CustomerRepository customerRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.saleRepository = saleRepository;
		this.carRepository = carRepository;
		this.customerRepository = customerRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("");
	}
	
	@Override
	public void seedSale(int number) {
		List<Sale> sales = this.saleRepository.findAll();
		if (sales.isEmpty()) {
			for (int i = 0; i < number; i++) {
				Sale sale = new Sale();
				sale.setCar(getRandomCar());
				sale.setCustomer(getRandomCustomer());
				sale.setDiscount(getRandomDiscount());
				this.saleRepository.saveAndFlush(sale);
			}
		}
	}
	
	private Discount getRandomDiscount() {
		Random random = new Random();
		int number = random.nextInt(8);
		
		Discount discount = Discount.values()[number];
		return discount;
	}
	
	private Customer getRandomCustomer() {
		Random random = new Random();
		long customerId = random.nextInt((int) (this.customerRepository.count() - 1)) + 1;
		
		return this.customerRepository.getOne(customerId);
	}
	
	private Car getRandomCar() {
		Random random = new Random();
		long carId = random.nextInt((int) (this.carRepository.count() - 1)) + 1;
		
		return this.carRepository.getOne(carId);
	}
}

package softuni.cardealer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.cardealer.services.*;

@Controller
public class CarDealerController implements CommandLineRunner {
	private final CarService carService;
	private final CustomerService customerService;
	private final PartService partService;
	private final SaleService saleService;
	private final SupplierService supplierService;
	
	@Autowired
	public CarDealerController(CarService carService, CustomerService customerService, PartService partService, SaleService saleService, SupplierService supplierService) {
		this.carService = carService;
		this.customerService = customerService;
		this.partService = partService;
		this.saleService = saleService;
		this.supplierService = supplierService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.supplierService.seedSupplier();
		this.partService.seedPart();
		this.carService.seedCar();
		this.customerService.seedCustomer();
		this.saleService.seedSale(50);
//		this.customerService.getOrderedCustomers();
//		this.carService.getToyotaCars("Toyota");
//		this.supplierService.getLocalSuppliers();
//		this.carService.getCarsWithParts();
		
	}
}

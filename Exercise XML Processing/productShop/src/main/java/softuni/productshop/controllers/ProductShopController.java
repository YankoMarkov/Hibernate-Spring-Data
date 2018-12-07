package softuni.productshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.productshop.services.CategoryService;
import softuni.productshop.services.ProductService;
import softuni.productshop.services.UserService;

import java.math.BigDecimal;

@Controller
public class ProductShopController implements CommandLineRunner {
	private final UserService userService;
	private final ProductService productService;
	private final CategoryService categoryService;
	
	@Autowired
	public ProductShopController(UserService userService, ProductService productService, CategoryService categoryService) {
		this.userService = userService;
		this.productService = productService;
		this.categoryService = categoryService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.userService.seedUsers();
		this.categoryService.seedCategory();
		this.productService.seedProducts();
		this.productService.productsByRangeWithoutBuyer(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
		this.productService.productSuccessfullySold();
		this.categoryService.categoryByProductCount();
		this.userService.usersAndProducts();
	}
}

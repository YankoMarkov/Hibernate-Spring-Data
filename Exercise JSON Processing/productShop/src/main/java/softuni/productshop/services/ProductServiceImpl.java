package softuni.productshop.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.ProductCreateDto;
import softuni.productshop.models.dtos.view.ProductInRangeDto;
import softuni.productshop.models.dtos.view.SellerDto;
import softuni.productshop.models.dtos.view.SoldProductDto;
import softuni.productshop.models.entities.Category;
import softuni.productshop.models.entities.Product;
import softuni.productshop.models.entities.User;
import softuni.productshop.repositories.CategoryRepository;
import softuni.productshop.repositories.ProductRepository;
import softuni.productshop.repositories.UserRepository;
import softuni.productshop.util.FileUtil;
import softuni.productshop.util.ValidatorUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/products.json");
	}
	
	@Override
	@Transactional
	public void seedProducts() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		List<ProductCreateDto> productCreateDtos = Arrays.asList(this.gson.fromJson(jsonString, ProductCreateDto[].class));
		
		for (ProductCreateDto productCreateDto : productCreateDtos) {
			if (!this.validatorUtil.isValid(productCreateDto)) {
				for (ConstraintViolation<ProductCreateDto> violation : this.validatorUtil.violations(productCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Product product = this.modelMapper.map(productCreateDto, Product.class);
			boolean exist = this.productRepository.existsByName(product.getName());
			if (!exist) {
				product.setSeller(getRandomUser());
				Random random = new Random();
				if (random.nextInt() % 2 == 0) {
					product.setBuyer(getRandomUser());
				}
				product.setCategories(new HashSet<>(getRandomCategories()));
				this.productRepository.saveAndFlush(product);
			}
		}
	}
	
	@Override
	public void productsByRangeWithoutBuyer(BigDecimal min, BigDecimal max) throws IOException {
		List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(min, max);
		List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
		
		for (Product product : products) {
			ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
			productInRangeDto.setSeller(String.format("%s %s",
					product.getSeller().getFirstName(),
					product.getSeller().getLastName()));
			productInRangeDtos.add(productInRangeDto);
		}
		File writeFilePath = new File("src/main/resources/output/product-in-range.json");
		String jsonString = this.gson.toJson(productInRangeDtos);
		this.file.writeFile(jsonString, writeFilePath);
	}
	
	@Override
	public void productSuccessfullySold() throws IOException {
		List<Product> products = this.productRepository.findAllByBuyerIsNotNull();
		List<SellerDto> sellerDtos = new ArrayList<>();
		
		for (Product product : products) {
			boolean exist = false;
			SellerDto sellerDto = new SellerDto();
			SoldProductDto soldProductDto = new SoldProductDto();
			soldProductDto.setName(product.getName());
			soldProductDto.setPrice(product.getPrice());
			soldProductDto.setBuyerFirstName(product.getBuyer().getFirstName());
			soldProductDto.setBuyerLastName(product.getBuyer().getLastName());
			sellerDto.setFirstName(product.getSeller().getFirstName());
			sellerDto.setLastName(product.getSeller().getLastName());
			for (SellerDto dto : sellerDtos) {
				if (dto.getFirstName() == null) {
					if (dto.getLastName().equals(sellerDto.getLastName())) {
						dto.getSoldProductDtos().add(soldProductDto);
						exist = true;
					}
				} else {
					if (dto.getFirstName().equals(sellerDto.getFirstName()) &&
							dto.getLastName().equals(sellerDto.getLastName())) {
						dto.getSoldProductDtos().add(soldProductDto);
						exist = true;
					}
				}
			}
			if (!exist) {
				sellerDto.getSoldProductDtos().add(soldProductDto);
				sellerDtos.add(sellerDto);
			}
		}
		File writeFilePath = new File("src/main/resources/output/users-sold-products.json");
		String jsonString = this.gson.toJson(sellerDtos);
		this.file.writeFile(jsonString, writeFilePath);
	}
	
	private User getRandomUser() {
		Random random = new Random();
		int userId = random.nextInt((int) (this.userRepository.count() - 1)) + 1;
		
		return this.userRepository.getOne(userId);
	}
	
	private List<Category> getRandomCategories() {
		Random random = new Random();
		List<Category> categories = new ArrayList<>();
		
		int count = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;
		
		for (int i = 0; i < count; i++) {
			int categoryId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;
			Category category = this.categoryRepository.getOne(categoryId);
			categories.add(category);
		}
		return categories;
	}
}

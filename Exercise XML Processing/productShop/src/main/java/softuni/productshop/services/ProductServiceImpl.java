package softuni.productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.ProductCreateDto;
import softuni.productshop.models.dtos.binding.ProductImportDto;
import softuni.productshop.models.dtos.view.*;
import softuni.productshop.models.entities.Category;
import softuni.productshop.models.entities.Product;
import softuni.productshop.models.entities.User;
import softuni.productshop.repositories.CategoryRepository;
import softuni.productshop.repositories.ProductRepository;
import softuni.productshop.repositories.UserRepository;
import softuni.productshop.util.ValidatorUtil;
import softuni.productshop.util.XMLParser;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
	private final String PRODUCT_XML_FILE_PATH = "src/main/resources/input/products.xml";
	private final String PRODUCT_IN_RANGE_PATH = "src/main/resources/output/products-in-range.xml";
	private final String PRODUCT_SUCCESSFULLY_SOLD_PATH = "src/main/resources/output/users-sold-products.xml";
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final XMLParser xmlParser;
	
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, XMLParser xmlParser) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.xmlParser = xmlParser;
	}
	
	@Override
	@Transactional
	public void seedProducts() throws IOException, JAXBException {
		ProductCreateDto productCreateDto = this.xmlParser.readXML(ProductCreateDto.class, this.PRODUCT_XML_FILE_PATH);
		
		for (ProductImportDto productImportDto : productCreateDto.getProductImportDtos()) {
			if (!this.validatorUtil.isValid(productImportDto)) {
				for (ConstraintViolation<ProductImportDto> violation : this.validatorUtil.violations(productImportDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Product product = this.modelMapper.map(productImportDto, Product.class);
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
	public void productsByRangeWithoutBuyer(BigDecimal min, BigDecimal max) throws JAXBException {
		List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(min, max);
		ProductInRangeRootDto productInRangeRootDto = new ProductInRangeRootDto();
		List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
		
		for (Product product : products) {
			ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
			productInRangeDto.setSeller(String.format("%s %s",
					product.getSeller().getFirstName(),
					product.getSeller().getLastName()));
			productInRangeDtos.add(productInRangeDto);
		}
		productInRangeRootDto.setProductInRangeDtos(productInRangeDtos);
		this.xmlParser.writeXML(productInRangeRootDto, ProductInRangeRootDto.class, this.PRODUCT_IN_RANGE_PATH);
	}
	
	@Override
	public void productSuccessfullySold() throws JAXBException {
		List<Product> products = this.productRepository.findAllByBuyerIsNotNull();
		SellerRootDto sellerRootDtos = new SellerRootDto();
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
			for (SellerDto importDto : sellerDtos) {
				if (importDto.getFirstName() == null) {
					if (importDto.getLastName().equals(sellerDto.getLastName())) {
						importDto.getSoldProductDtos().add(soldProductDto);
						exist = true;
					}
				} else {
					if (importDto.getFirstName().equals(sellerDto.getFirstName()) &&
							importDto.getLastName().equals(sellerDto.getLastName())) {
						importDto.getSoldProductDtos().add(soldProductDto);
						exist = true;
					}
				}
			}
			if (!exist) {
				sellerDto.getSoldProductDtos().add(soldProductDto);
				sellerDtos.add(sellerDto);
			}
		}
		sellerRootDtos.setSellerDtos(sellerDtos);
		this.xmlParser.writeXML(sellerRootDtos, SellerRootDto.class, this.PRODUCT_SUCCESSFULLY_SOLD_PATH);
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

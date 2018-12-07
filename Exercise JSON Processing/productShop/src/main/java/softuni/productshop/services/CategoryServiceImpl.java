package softuni.productshop.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.CategoryCreateDto;
import softuni.productshop.models.dtos.view.CategoryByProductCountDto;
import softuni.productshop.models.entities.Category;
import softuni.productshop.models.entities.Product;
import softuni.productshop.repositories.CategoryRepository;
import softuni.productshop.repositories.ProductRepository;
import softuni.productshop.util.FileUtil;
import softuni.productshop.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final Gson gson;
	private final FileUtil fileUtil;
	private final File filePath;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, Gson gson, FileUtil fileUtil) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.gson = gson;
		this.fileUtil = fileUtil;
		this.filePath = new File("src/main/resources/input/categories.json");
	}
	
	@Override
	public void seedCategory() throws IOException {
		String jsonString = this.fileUtil.readFile(this.filePath);
		List<CategoryCreateDto> categoryCreateDtos = Arrays.asList(this.gson.fromJson(jsonString, CategoryCreateDto[].class));
		
		for (CategoryCreateDto categoryCreateDto : categoryCreateDtos) {
			if (!this.validatorUtil.isValid(categoryCreateDto)) {
				for (ConstraintViolation<CategoryCreateDto> violation : this.validatorUtil.violations(categoryCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Category category = this.modelMapper.map(categoryCreateDto, Category.class);
			boolean exist = this.categoryRepository.existsByName(category.getName());
			if (!exist) {
				this.categoryRepository.saveAndFlush(category);
			}
		}
	}
	
	@Override
	public void categoryByProductCount() throws IOException {
		List<Category> categories = this.categoryRepository.findAllByOrderByProductsDesc();
		List<CategoryByProductCountDto> categoryByProductCountDtos = new ArrayList<>();
		
		for (Category category : categories) {
			CategoryByProductCountDto categoryByProductCountDto = new CategoryByProductCountDto();
			
			categoryByProductCountDto.setCategory(category.getName());
			categoryByProductCountDto.setProductsCount(category.getProducts().size());
			BigDecimal price = category.getProducts().stream()
					.map(Product::getPrice)
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO);
			BigDecimal averagePrice = new BigDecimal(0);
			if (category.getProducts().size() > 0) {
				averagePrice = price.divide(BigDecimal.valueOf(category.getProducts().size()), RoundingMode.CEILING);
			}
			categoryByProductCountDto.setAveragePrice(averagePrice);
			categoryByProductCountDto.setTotalRevenue(price);
			categoryByProductCountDtos.add(categoryByProductCountDto);
		}
		File filePath = new File("src/main/resources/output/categories-by-products.json");
		String jsonString = this.gson.toJson(categoryByProductCountDtos);
		this.fileUtil.writeFile(jsonString, filePath);
	}
}

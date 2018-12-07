package softuni.productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.productshop.models.dtos.binding.CategoryCreateDto;
import softuni.productshop.models.dtos.binding.CategoryImportDto;
import softuni.productshop.models.dtos.view.CategoryByProductCountDto;
import softuni.productshop.models.dtos.view.CategoryByProductCountRootDto;
import softuni.productshop.models.entities.Category;
import softuni.productshop.models.entities.Product;
import softuni.productshop.repositories.CategoryRepository;
import softuni.productshop.util.ValidatorUtil;
import softuni.productshop.util.XMLParser;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final String CATEGORY_XML_FILE_PATH = "src/main/resources/input/categories.xml";
	private final String CATEGORY_BY_PRODUCT_COUNT_PATH = "src/main/resources/output/categories-by-products.xml";
	private final CategoryRepository categoryRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final XMLParser xmlParser;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, XMLParser xmlParser) {
		this.categoryRepository = categoryRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.xmlParser = xmlParser;
	}
	
	@Override
	public void seedCategory() throws IOException, JAXBException {
		CategoryCreateDto categoryCreateDto = this.xmlParser.readXML(CategoryCreateDto.class, this.CATEGORY_XML_FILE_PATH);
		
		for (CategoryImportDto categoryImportDto : categoryCreateDto.getCategoryImportDtos()) {
			if (!this.validatorUtil.isValid(categoryImportDto)) {
				for (ConstraintViolation<CategoryImportDto> violation : this.validatorUtil.violations(categoryImportDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Category category = this.modelMapper.map(categoryImportDto, Category.class);
			boolean exist = this.categoryRepository.existsByName(category.getName());
			if (!exist) {
				this.categoryRepository.saveAndFlush(category);
			}
		}
	}
	
	@Override
	public void categoryByProductCount() throws IOException, JAXBException {
		List<Category> categories = this.categoryRepository.findAllByOrderByProductsDesc();
		CategoryByProductCountRootDto categoryByProductCountRootDtos = new CategoryByProductCountRootDto();
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
		categoryByProductCountRootDtos.setCategoryByProductCountDtos(categoryByProductCountDtos);
		this.xmlParser.writeXML(categoryByProductCountRootDtos, CategoryByProductCountRootDto.class, this.CATEGORY_BY_PRODUCT_COUNT_PATH);
	}
}

package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public String exportCategoriesByCountOfItems() {
		StringBuilder result = new StringBuilder();
		List<Category> categories = this.categoryRepository.getAllCategoryByCountOfItems();
		
		for (Category category : categories) {
			result.append(String.format("Category: %s", category.getName())).append(System.lineSeparator());
			for (Item item : category.getItems()) {
				result.append(String.format("--- Item Name: %s", item.getName())).append(System.lineSeparator());
				result.append(String.format("--- Item Price: %s", item.getPrice())).append(System.lineSeparator());
				result.append(System.lineSeparator());
			}
		}
		return result.toString().trim();
	}
}

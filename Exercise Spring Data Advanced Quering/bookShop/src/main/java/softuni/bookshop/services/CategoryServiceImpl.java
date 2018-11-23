package softuni.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.bookshop.models.Category;
import softuni.bookshop.repositories.CategoryRepository;
import softuni.bookshop.util.FileUtil;

import java.io.File;
import java.io.IOException;

@Component
public class CategoryServiceImpl implements CategoryService {
	private final File categoriesFilePath = new File("src/main/resources/files/categories.txt");
	
	private final CategoryRepository categoryRepository;
	private final FileUtil fileUtil;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
		this.categoryRepository = categoryRepository;
		this.fileUtil = fileUtil;
	}
	
	@Override
	public void seedCategories() throws IOException {
		if (this.categoryRepository.count() != 0) {
			return;
		}
		String[] categoryFileContent = this.fileUtil.getFileContent(categoriesFilePath);
		
		for (String categories : categoryFileContent) {
			
			Category category = new Category();
			category.setName(categories);
			this.categoryRepository.saveAndFlush(category);
		}
	}
}

package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.domain.dtos.bindings.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
	private final String ITEM_IMPORT_FILE_PATH = "src/main/resources/files/items.json";
	private final ItemRepository itemRepository;
	private final CategoryRepository categoryRepository;
	private final Gson gson;
	private final FileUtil fileUtil;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;
	
	@Autowired
	public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, Gson gson, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.itemRepository = itemRepository;
		this.categoryRepository = categoryRepository;
		this.gson = gson;
		this.fileUtil = fileUtil;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}
	
	@Override
	public Boolean itemsAreImported() {
		return this.itemRepository.count() > 0;
	}
	
	@Override
	public String readItemsJsonFile() throws IOException {
		return this.fileUtil.readFile(this.ITEM_IMPORT_FILE_PATH);
	}
	
	@Override
	public String importItems(String items) {
		StringBuilder result = new StringBuilder();
		ItemImportDto[] itemImportDtos = this.gson.fromJson(items, ItemImportDto[].class);
		
		for (ItemImportDto itemImportDto : itemImportDtos) {
			if (!this.validationUtil.isValid(itemImportDto)) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			boolean exist = this.itemRepository.existsByName(itemImportDto.getName());
			if (exist) {
				continue;
			}
			Item item = this.modelMapper.map(itemImportDto, Item.class);
			Category category = this.categoryRepository.findByName(itemImportDto.getCategory()).orElse(null);
			
			if (category == null) {
				category = new Category();
				category.setName(itemImportDto.getCategory());
				category.getItems().add(item);
				this.categoryRepository.saveAndFlush(category);
			} else {
				category.getItems().add(item);
				this.categoryRepository.saveAndFlush(category);
			}
			item.setCategory(category);
			this.itemRepository.saveAndFlush(item);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					item.getName())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

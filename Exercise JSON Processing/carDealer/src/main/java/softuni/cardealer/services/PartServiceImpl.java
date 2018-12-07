package softuni.cardealer.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.models.dtos.bindings.PartCreateDto;
import softuni.cardealer.models.entities.Part;
import softuni.cardealer.models.entities.Supplier;
import softuni.cardealer.repositories.PartRepository;
import softuni.cardealer.repositories.SupplierRepository;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
	private final PartRepository partRepository;
	private final SupplierRepository supplierRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.partRepository = partRepository;
		this.supplierRepository = supplierRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/parts.json");
	}
	
	@Override
	public void seedPart() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		PartCreateDto[] partCreateDtos = this.gson.fromJson(jsonString, PartCreateDto[].class);
		
		for (PartCreateDto partCreateDto : partCreateDtos) {
			if (!this.validatorUtil.isValid(partCreateDto)) {
				for (ConstraintViolation<PartCreateDto> violation : this.validatorUtil.violations(partCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Part part = this.modelMapper.map(partCreateDto, Part.class);
			boolean exist = this.partRepository.existsByName(part.getName());
			if (!exist) {
				part.setSupplier(getRandomSupplier());
				this.partRepository.saveAndFlush(part);
			}
		}
	}
	
	private Supplier getRandomSupplier() {
		Random random = new Random();
		long supplierId = random.nextInt((int) (this.supplierRepository.count() - 1)) + 1;
		
		return this.supplierRepository.getOne(supplierId);
	}
}

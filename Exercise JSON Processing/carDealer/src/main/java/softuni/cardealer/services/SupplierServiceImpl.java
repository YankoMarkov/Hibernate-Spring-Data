package softuni.cardealer.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.models.dtos.bindings.SupplierCreateDto;
import softuni.cardealer.models.dtos.views.LocalSupplierDto;
import softuni.cardealer.models.entities.Supplier;
import softuni.cardealer.repositories.SupplierRepository;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
	private final SupplierRepository supplierRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.supplierRepository = supplierRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/suppliers.json");
	}
	
	@Override
	public void seedSupplier() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		SupplierCreateDto[] supplierCreateDtos = this.gson.fromJson(jsonString, SupplierCreateDto[].class);
		
		for (SupplierCreateDto supplierCreateDto : supplierCreateDtos) {
			if (!this.validatorUtil.isValid(supplierCreateDto)) {
				for (ConstraintViolation<SupplierCreateDto> violation : this.validatorUtil.violations(supplierCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Supplier supplier = this.modelMapper.map(supplierCreateDto, Supplier.class);
			boolean exist = this.supplierRepository.existsByName(supplier.getName());
			if (!exist) {
				this.supplierRepository.saveAndFlush(supplier);
			}
		}
	}
	
	@Override
	public void getLocalSuppliers() throws IOException {
		List<Supplier> suppliers = this.supplierRepository.findAllByImporterIsFalse();
		List<LocalSupplierDto> localSupplierDtos = new ArrayList<>();
		
		for (Supplier supplier : suppliers) {
			LocalSupplierDto localSupplierDto = this.modelMapper.map(supplier, LocalSupplierDto.class);
			localSupplierDto.setPartsCount(supplier.getParts().size());
			localSupplierDtos.add(localSupplierDto);
		}
		File writePathFile = new File("src/main/resources/output/local-suppliers.json");
		String jsonString = this.gson.toJson(localSupplierDtos);
		this.file.writeFile(jsonString, writePathFile);
	}
}

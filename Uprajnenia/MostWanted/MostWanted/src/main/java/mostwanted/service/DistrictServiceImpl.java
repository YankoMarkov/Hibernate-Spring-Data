package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {
	private final String DISTRICT_INPUT_FILE_PATH = "src/main/resources/files/districts.json";
	private final DistrictRepository districtRepository;
	private final TownRepository townRepository;
	private final FileUtil fileUtil;
	private final Gson gson;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;
	
	@Autowired
	public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.districtRepository = districtRepository;
		this.townRepository = townRepository;
		this.fileUtil = fileUtil;
		this.gson = gson;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}
	
	@Override
	public Boolean districtsAreImported() {
		return this.districtRepository.count() != 0;
	}
	
	@Override
	public String readDistrictsJsonFile() throws IOException {
		return this.fileUtil.readFile(DISTRICT_INPUT_FILE_PATH);
	}
	
	@Override
	public String importDistricts(String districtsFileContent) {
		StringBuilder result = new StringBuilder();
		DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);
		
		for (DistrictImportDto districtImportDto : districtImportDtos) {
			District district = this.districtRepository.findByName(districtImportDto.getName()).orElse(null);
			if (district != null) {
				result.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			Town town = this.townRepository.findByName(districtImportDto.getTownName()).orElse(null);
			if (!this.validationUtil.isValid(districtImportDto) || town == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			district = this.modelMapper.map(districtImportDto, District.class);
			district.setTown(town);
			this.districtRepository.saveAndFlush(district);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					district.getClass().getSimpleName(),
					district.getName())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

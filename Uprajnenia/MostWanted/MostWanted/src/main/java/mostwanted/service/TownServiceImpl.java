package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {
	private final String TOWN_INPUT_FILE_PATH = "src/main/resources/files/towns.json";
	private final TownRepository townRepository;
	private final FileUtil fileUtil;
	private final ModelMapper modelMapper;
	private final Gson gson;
	private final ValidationUtil validationUtil;
	
	@Autowired
	public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
		this.townRepository = townRepository;
		this.fileUtil = fileUtil;
		this.modelMapper = modelMapper;
		this.gson = gson;
		this.validationUtil = validationUtil;
	}
	
	@Override
	public Boolean townsAreImported() {
		return this.townRepository.count() != 0;
	}
	
	@Override
	public String readTownsJsonFile() throws IOException {
		return fileUtil.readFile(TOWN_INPUT_FILE_PATH);
	}
	
	@Override
	public String importTowns(String townsFileContent) {
		StringBuilder result = new StringBuilder();
		TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);
		
		for (TownImportDto townImportDto : townImportDtos) {
			Town town = this.townRepository.findByName(townImportDto.getName()).orElse(null);
			if (town != null) {
				result.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			if (!this.validationUtil.isValid(townImportDto)) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			town = this.modelMapper.map(townImportDto, Town.class);
			this.townRepository.saveAndFlush(town);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					town.getClass().getSimpleName(),
					town.getName())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
	
	@Override
	public String exportRacingTowns() {
		StringBuilder result = new StringBuilder();
		List<Town> towns = this.townRepository.getAllTownsWithRacers();
		
		for (Town town : towns) {
			result.append(String.format("Name:%s", town.getName())).append(System.lineSeparator());
			result.append(String.format("Racers:%d", town.getRacers().size())).append(System.lineSeparator());
			result.append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

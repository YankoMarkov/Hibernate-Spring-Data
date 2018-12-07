package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RacerServiceImpl implements RacerService {
	private final String RACER_INPUT_FILE_PATH = "src/main/resources/files/racers.json";
	private final RacerRepository racerRepository;
	private final TownRepository townRepository;
	private final FileUtil fileUtil;
	private final ModelMapper modelMapper;
	private final Gson gson;
	private final ValidationUtil validationUtil;
	
	@Autowired
	public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
		this.racerRepository = racerRepository;
		this.townRepository = townRepository;
		this.fileUtil = fileUtil;
		this.modelMapper = modelMapper;
		this.gson = gson;
		this.validationUtil = validationUtil;
	}
	
	@Override
	public Boolean racersAreImported() {
		return this.racerRepository.count() != 0;
	}
	
	@Override
	public String readRacersJsonFile() throws IOException {
		return this.fileUtil.readFile(RACER_INPUT_FILE_PATH);
	}
	
	@Override
	public String importRacers(String racersFileContent) {
		StringBuilder result = new StringBuilder();
		RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);
		
		for (RacerImportDto racerImportDto : racerImportDtos) {
			Racer racer = this.racerRepository.findByName(racerImportDto.getName()).orElse(null);
			if (racer != null) {
				result.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
			}
			Town town = this.townRepository.findByName(racerImportDto.getHomeTown()).orElse(null);
			if (!this.validationUtil.isValid(racerImportDto) || town == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
			}
			racer = this.modelMapper.map(racerImportDto, Racer.class);
			racer.setHomeTown(town);
			this.racerRepository.saveAndFlush(racer);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					racer.getClass().getSimpleName(),
					racer.getName())).append(System.lineSeparator());
		}
		
		return result.toString().trim();
	}
	
	@Override
	public String exportRacingCars() {
		StringBuilder result = new StringBuilder();
		List<Racer> racers = this.racerRepository.getAllRacersWithCars();
		
		for (Racer racer : racers) {
			if (racer.getAge() != null) {
				resultString(result, racer, "Name:%s, %d");
			} else {
				resultString(result, racer, "Name:%s");
			}
		}
		return result.toString().trim();
	}
	
	private void resultString(StringBuilder result, Racer racer, String s) {
		result.append(String.format(s, racer.getName(), racer.getAge())).append(System.lineSeparator());
		result.append("Cars:").append(System.lineSeparator());
		for (Car car : racer.getCars()) {
			result.append(String.format("%s %s %s", car.getBrand(), car.getModel(), car.getYearOfProduction())).append(System.lineSeparator());
		}
		result.append(System.lineSeparator());
	}
}

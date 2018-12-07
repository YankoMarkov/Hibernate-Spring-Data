package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {
	private final String CAR_INPUT_FILE_PATH = "src/main/resources/files/cars.json";
	private final CarRepository carRepository;
	private final RacerRepository racerRepository;
	private final FileUtil fileUtil;
	private final Gson gson;
	private final ValidationUtil validationUtil;
	private final ModelMapper modelMapper;
	
	@Autowired
	public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
		this.carRepository = carRepository;
		this.racerRepository = racerRepository;
		this.fileUtil = fileUtil;
		this.gson = gson;
		this.validationUtil = validationUtil;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Boolean carsAreImported() {
		return this.carRepository.count() != 0;
	}
	
	@Override
	public String readCarsJsonFile() throws IOException {
		return this.fileUtil.readFile(CAR_INPUT_FILE_PATH);
	}
	
	@Override
	public String importCars(String carsFileContent) {
		StringBuilder result = new StringBuilder();
		CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);
		
		for (CarImportDto carImportDto : carImportDtos) {
			Racer racer = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);
			if (!this.validationUtil.isValid(carImportDto) || racer == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
			}
			Car car = this.modelMapper.map(carImportDto, Car.class);
			car.setRacer(racer);
			this.carRepository.saveAndFlush(car);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE + " %s @ %d",
					car.getClass().getSimpleName(),
					car.getBrand(),
					car.getModel(),
					car.getYearOfProduction())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

package softuni.cardealer.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.models.dtos.bindings.CarCreateDto;
import softuni.cardealer.models.dtos.views.CarDto;
import softuni.cardealer.models.dtos.views.CarWithPartsDto;
import softuni.cardealer.models.dtos.views.PartDto;
import softuni.cardealer.models.dtos.views.ToyotaCarDto;
import softuni.cardealer.models.entities.Car;
import softuni.cardealer.models.entities.Part;
import softuni.cardealer.repositories.CarRepository;
import softuni.cardealer.repositories.PartRepository;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.ValidatorUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
	private final CarRepository carRepository;
	private final PartRepository partRepository;
	private final ValidatorUtil validatorUtil;
	private final ModelMapper modelMapper;
	private final FileUtil file;
	private final Gson gson;
	private final File filePath;
	
	@Autowired
	public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil file, Gson gson) {
		this.carRepository = carRepository;
		this.partRepository = partRepository;
		this.validatorUtil = validatorUtil;
		this.modelMapper = modelMapper;
		this.file = file;
		this.gson = gson;
		this.filePath = new File("src/main/resources/input/cars.json");
	}
	
	@Override
	@Transactional
	public void seedCar() throws IOException {
		String jsonString = this.file.readFile(this.filePath);
		CarCreateDto[] carCreateDtos = this.gson.fromJson(jsonString, CarCreateDto[].class);
		
		for (CarCreateDto carCreateDto : carCreateDtos) {
			if (!this.validatorUtil.isValid(carCreateDto)) {
				for (ConstraintViolation<CarCreateDto> violation : this.validatorUtil.violations(carCreateDto)) {
					throw new IllegalArgumentException(violation.getMessage());
				}
			}
			Car car = this.modelMapper.map(carCreateDto, Car.class);
			boolean exist = this.carRepository.existsByMakeAndModelAndTravelledDistance(car.getMake(), car.getModel(), car.getTravelledDistance());
			if (!exist) {
				car.setParts(getRandomParts());
				this.carRepository.saveAndFlush(car);
			}
		}
	}
	
	@Override
	public void getToyotaCars(String make) throws IOException {
		List<Car> cars = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make);
		List<ToyotaCarDto> toyotaCarDtos = new ArrayList<>();
		
		for (Car car : cars) {
			ToyotaCarDto toyotaCarDto = this.modelMapper.map(car, ToyotaCarDto.class);
			toyotaCarDtos.add(toyotaCarDto);
		}
		File writeFilePath = new File("src/main/resources/output/toyota-cars.json");
		String jsonString = this.gson.toJson(toyotaCarDtos);
		this.file.writeFile(jsonString, writeFilePath);
	}
	
	@Override
	public void getCarsWithParts() throws IOException {
		List<Car> cars = this.carRepository.findAll();
		List<CarWithPartsDto> carWithPartsDtos = new ArrayList<>();
		
		for (Car car : cars) {
			CarWithPartsDto carWithPartsDto = new CarWithPartsDto();
			CarDto carDto = this.modelMapper.map(car, CarDto.class);
			List<PartDto> partDtos = new ArrayList<>();
			for (Part part : car.getParts()) {
				PartDto partDto = this.modelMapper.map(part, PartDto.class);
				partDtos.add(partDto);
			}
			carWithPartsDto.setCar(carDto);
			carWithPartsDto.setParts(partDtos);
			carWithPartsDtos.add(carWithPartsDto);
		}
		File writePathFile = new File("src/main/resources/output/cars-and-parts.json");
		String jsonString = this.gson.toJson(carWithPartsDtos);
		this.file.writeFile(jsonString, writePathFile);
	}
	
	private Set<Part> getRandomParts() {
		Random random = new Random();
		Set<Part> parts = new HashSet<>();
		
		int count = random.nextInt(20 - 10) + 10;
		
		for (int i = 0; i < count; i++) {
			long partId = random.nextInt((int) (this.partRepository.count() - 1)) + 1;
			Part part = this.partRepository.getOne(partId);
			parts.add(part);
		}
		return parts;
	}
}

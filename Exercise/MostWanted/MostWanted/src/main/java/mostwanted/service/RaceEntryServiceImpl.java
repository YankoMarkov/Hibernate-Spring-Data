package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.raceEntry.RaceEntryImportDto;
import mostwanted.domain.dtos.bindings.raceEntry.RaceEntryRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
	private final String RACE_ENTRY_INPUT_FILE_PATH = "src/main/resources/files/race-entries.xml";
	private final RaceEntryRepository raceEntryRepository;
	private final RacerRepository racerRepository;
	private final CarRepository carRepository;
	private final FileUtil fileUtil;
	private final XmlParser xmlParser;
	private final ModelMapper modelMapper;
	
	@Autowired
	public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository, CarRepository carRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper) {
		this.raceEntryRepository = raceEntryRepository;
		this.racerRepository = racerRepository;
		this.carRepository = carRepository;
		this.fileUtil = fileUtil;
		this.xmlParser = xmlParser;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Boolean raceEntriesAreImported() {
		return this.raceEntryRepository.count() != 0;
	}
	
	@Override
	public String readRaceEntriesXmlFile() throws IOException {
		return this.fileUtil.readFile(RACE_ENTRY_INPUT_FILE_PATH);
	}
	
	@Override
	public String importRaceEntries() throws JAXBException, FileNotFoundException {
		StringBuilder result = new StringBuilder();
		RaceEntryRootDto raceEntryRootDto = this.xmlParser.parseXml(RaceEntryRootDto.class, RACE_ENTRY_INPUT_FILE_PATH);
		
		for (RaceEntryImportDto raceEntryImportDto : raceEntryRootDto.getRaceEntryImportDtos()) {
			Racer racer = this.racerRepository.findByName(raceEntryImportDto.getRacer()).orElse(null);
			Car car = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
			if (racer == null || car == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			RaceEntry raceEntry = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
			raceEntry.setCar(car);
			raceEntry.setRacer(racer);
			raceEntry.setRace(null);
			raceEntry = this.raceEntryRepository.saveAndFlush(raceEntry);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					raceEntry.getClass().getSimpleName(),
					raceEntry.getId())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.bindings.race.EntryImportDto;
import mostwanted.domain.dtos.bindings.race.RaceImportDto;
import mostwanted.domain.dtos.bindings.race.RaceRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class RaceServiceImpl implements RaceService {
	private final String RACE_INPUT_FILE_PATH = "src/main/resources/files/races.xml";
	private final RaceRepository raceRepository;
	private final DistrictRepository districtRepository;
	private final RaceEntryRepository raceEntryRepository;
	private final FileUtil fileUtil;
	private final XmlParser xmlParser;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;
	
	@Autowired
	public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.raceRepository = raceRepository;
		this.districtRepository = districtRepository;
		this.raceEntryRepository = raceEntryRepository;
		this.fileUtil = fileUtil;
		this.xmlParser = xmlParser;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}
	
	@Override
	public Boolean racesAreImported() {
		return this.raceRepository.count() != 0;
	}
	
	@Override
	public String readRacesXmlFile() throws IOException {
		return this.fileUtil.readFile(RACE_INPUT_FILE_PATH);
	}
	
	@Override
	public String importRaces() throws JAXBException, FileNotFoundException {
		StringBuilder result = new StringBuilder();
		RaceRootDto raceRootDto = this.xmlParser.parseXml(RaceRootDto.class, this.RACE_INPUT_FILE_PATH);
		
		for (RaceImportDto raceImportDto : raceRootDto.getRaceImportDtos()) {
			District district = this.districtRepository.findByName(raceImportDto.getDistrictName()).orElse(null);
			if (!this.validationUtil.isValid(raceImportDto) || district == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			Race race = this.modelMapper.map(raceImportDto, Race.class);
			Set<RaceEntry> raceEntries = new HashSet<>();
			for (EntryImportDto entryImportDto : raceImportDto.getEntryRootDtos().getEntryImportDtos()) {
				RaceEntry raceEntry = this.raceEntryRepository.findById(entryImportDto.getId()).orElse(null);
				if (raceEntry == null) {
					continue;
				}
				raceEntry.setRace(race);
				raceEntries.add(raceEntry);
			}
			race.setDistrict(district);
			race.setEntries(raceEntries);
			race = this.raceRepository.saveAndFlush(race);
			this.raceEntryRepository.saveAll(raceEntries);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					race.getClass().getSimpleName(),
					race.getId())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

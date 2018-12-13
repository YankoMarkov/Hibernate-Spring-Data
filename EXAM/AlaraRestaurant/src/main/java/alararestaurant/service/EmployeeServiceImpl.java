package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.domain.dtos.bindings.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final String EMPLOYEE_IMPORT_FILE_PATH = "src/main/resources/files/employees.json";
	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	private final Gson gson;
	private final FileUtil fileUtil;
	private final ValidationUtil validationUtil;
	private final ModelMapper modelMapper;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.positionRepository = positionRepository;
		this.gson = gson;
		this.fileUtil = fileUtil;
		this.validationUtil = validationUtil;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Boolean employeesAreImported() {
		return this.employeeRepository.count() > 0;
	}
	
	@Override
	public String readEmployeesJsonFile() throws IOException {
		return this.fileUtil.readFile(this.EMPLOYEE_IMPORT_FILE_PATH);
	}
	
	@Override
	public String importEmployees(String employees) {
		StringBuilder result = new StringBuilder();
		EmployeeImportDto[] employeeImportDtos = this.gson.fromJson(employees, EmployeeImportDto[].class);
		
		for (EmployeeImportDto employeeImportDto : employeeImportDtos) {
			if (!this.validationUtil.isValid(employeeImportDto)) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			Employee employee = this.modelMapper.map(employeeImportDto, Employee.class);
			Position position = this.positionRepository.findByName(employeeImportDto.getPosition()).orElse(null);
			
			if (position == null) {
				position = new Position();
				position.setName(employeeImportDto.getPosition());
				position.getEmployees().add(employee);
				this.positionRepository.saveAndFlush(position);
			} else {
				position.getEmployees().add(employee);
				this.positionRepository.saveAndFlush(position);
			}
			employee.setPosition(position);
			this.employeeRepository.saveAndFlush(employee);
			result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
					employee.getName())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}

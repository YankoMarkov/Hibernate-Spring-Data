package alararestaurant.service;

public interface EmployeeService {

    Boolean employeesAreImported();

    String readEmployeesJsonFile();

    String importEmployees(String employees);
}

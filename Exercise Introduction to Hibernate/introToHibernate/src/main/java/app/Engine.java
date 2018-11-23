package app;

import app.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.*;

public class Engine implements Runnable {
	
	private final EntityManager manager;
	
	public Engine(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		int number = Integer.valueOf(scan.nextLine());
		switch (number) {
			case 2:
				removeObjects();
				break;
			case 3:
				containsEmployee();
				break;
			case 4:
				employeesWithSalaryOver50000();
				break;
			case 5:
				employeesFromDepartment();
				break;
			case 6:
				addingNewAddressAndUpdatingEmployee();
				break;
			case 7:
				addressesWithEmployeeCount();
				break;
			case 8:
				getEmployeeWithProject();
				break;
			case 9:
				findLatest10Projects();
				break;
			case 10:
				increaseSalaries();
				break;
			case 11:
				removeTowns();
				break;
			case 12:
				findEmployeesByFirstName();
				break;
			case 13:
				employeesMaximumSalaries();
				break;
			default:
				System.out.println("Wrong number.");
				break;
		}
	}
	
	/**
	 * 2. Remove Objects
	 */
	private void removeObjects() {
		this.manager.getTransaction().begin();
		
		List<Town> towns = this.manager
				.createQuery("FROM Town WHERE length(name) > 5", Town.class)
				.getResultList();
		
		towns.forEach(town -> {
			this.manager.detach(town);
			town.setName(town.getName().toLowerCase());
			this.manager.merge(town);
		});
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 3. Contains Employee
	 */
	private void containsEmployee() {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		
		this.manager.getTransaction().begin();
		try {
			Employee employee = this.manager
					.createQuery("FROM Employee WHERE concat(firstName, ' ', lastName) = :name", Employee.class)
					.setParameter("name", name)
					.getSingleResult();
			System.out.println("Yes");
		} catch (NoResultException nre) {
			System.out.println("No");
		}
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 4. Employees with Salary Over 50 000
	 */
	private void employeesWithSalaryOver50000() {
		this.manager.getTransaction().begin();
		
		List<Employee> employees = this.manager
				.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
				.getResultList();
		
		employees.forEach(employee -> System.out.println(employee.getFirstName()));
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 5. Employees from Department
	 */
	private void employeesFromDepartment() {
		this.manager.getTransaction().begin();
		
		List<Employee> employees = this.manager
				.createQuery("FROM Employee WHERE department.name = 'Research and Development' ORDER BY salary, id", Employee.class)
				.getResultList();
		
		for (Employee employee : employees) {
			System.out.printf("%s %s from %s $%.2f%n",
					employee.getFirstName(), employee.getLastName(),
					employee.getDepartment().getName(), employee.getSalary());
		}
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 6. Adding a New Address and Updating Employee
	 */
	private void addingNewAddressAndUpdatingEmployee() {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		this.manager.getTransaction().begin();
		
		Address newAddress = new Address();
		newAddress.setText("Vitoshka 15");
		this.manager.persist(newAddress);
		
		Employee employee = this.manager
				.createQuery("FROM Employee WHERE lastName = :name", Employee.class)
				.setParameter("name", name)
				.getSingleResult();
		
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 7. Addresses with Employee Count
	 */
	private void addressesWithEmployeeCount() {
		this.manager.getTransaction().begin();
		
		List<Address> addresses = this.manager
				.createQuery("FROM Address ORDER BY employees.size DESC, id", Address.class)
				.setMaxResults(10)
				.getResultList();
		
		for (Address address : addresses) {
			System.out.printf("%s, %s - %d employees%n",
					address.getText(),
					address.getTown().getName(),
					address.getEmployees().size());
		}
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 8. Get Employee with Project
	 */
	private void getEmployeeWithProject() {
		Scanner scan = new Scanner(System.in);
		int number = Integer.valueOf(scan.nextLine());
		
		this.manager.getTransaction().begin();
		
		Employee employee = this.manager
				.createQuery("FROM Employee WHERE id = :number", Employee.class)
				.setParameter("number", number)
				.getSingleResult();
		
		System.out.printf("%s %s - %s%n",
				employee.getFirstName(),
				employee.getLastName(),
				employee.getJobTitle());
		employee.getProjects().stream()
				.sorted(Comparator.comparing(Project::getName))
				.forEach(a -> System.out.printf("\t%s%n", a.getName()));
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 9. Find Latest 10 Projects
	 */
	private void findLatest10Projects() {
		this.manager.getTransaction().begin();
		
		List<Project> projects = this.manager
				.createQuery("FROM Project WHERE endDate = null ORDER BY startDate DESC", Project.class)
				.setMaxResults(10)
				.getResultList();
		
		projects.stream()
				.sorted(Comparator.comparing(Project::getName))
				.forEach(a -> System.out.printf("Project name: %s%n" +
								" \tProject Description: %s%n" +
								" \tProject Start Date: %s%n" +
								" \tProject End Date: %s%n",
						a.getName(),
						a.getDescription(),
						a.getStartDate(),
						a.getEndDate()));
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 10. Increase Salaries
	 */
	private void increaseSalaries() {
		this.manager.getTransaction().begin();
		
		List<Employee> employees = this.manager
				.createQuery("FROM Employee WHERE department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
				.getResultList();
		
		for (Employee employee : employees) {
			employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
		}
		for (Employee employee : employees) {
			System.out.printf("%s %s ($%.2f)%n",
					employee.getFirstName(),
					employee.getLastName(),
					employee.getSalary());
		}
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 11. Remove Towns
	 */
	private void removeTowns() {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine().toLowerCase();
		this.manager.getTransaction().begin();
		
		List<Employee> employees = this.manager
				.createQuery("FROM Employee WHERE lower(address.town.name) = :name", Employee.class)
				.setParameter("name", name)
				.getResultList();
		
		List<Address> addresses = this.manager
				.createQuery("FROM Address WHERE lower(town.name) = :name", Address.class)
				.setParameter("name", name)
				.getResultList();
		
		Town town = this.manager
				.createQuery("FROM Town WHERE lower(name) = :name", Town.class)
				.setParameter("name", name)
				.getSingleResult();
		
		for (Employee employee : employees) {
			employee.setAddress(null);
		}
		
		for (Address address : addresses) {
			this.manager.remove(address);
		}
		this.manager.remove(town);
		
		System.out.printf("%d address in %s deleted", addresses.size(), name);
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 12. Find Employees by First Name
	 */
	private void findEmployeesByFirstName() {
		Scanner scan = new Scanner(System.in);
		String word = scan.nextLine().toLowerCase();
		this.manager.getTransaction().begin();
		
		List<Employee> employees = this.manager
				.createQuery("FROM Employee WHERE lower(firstName) LIKE '" + word + "%'", Employee.class)
				.getResultList();
		for (Employee employee : employees) {
			System.out.printf("%s %s - %s - ($%.2f)%n",
					employee.getFirstName(),
					employee.getLastName(),
					employee.getDepartment().getName(),
					employee.getSalary());
		}
		
		this.manager.getTransaction().commit();
	}
	
	/**
	 * 13. Employees Maximum Salaries
	 */
	private void employeesMaximumSalaries() {
		this.manager.getTransaction().begin();
		
		List<Department> departments = this.manager
				.createQuery("FROM Department", Department.class)
				.getResultList();
		
		Set<Department> newDepartments = new HashSet<>();
		
		for (Department department : departments) {
			department.getEmployees()
					.forEach(a -> {
						if (a.getSalary().compareTo(BigDecimal.valueOf(30000)) > 0 && a.getSalary().compareTo(BigDecimal.valueOf(70000)) < 0) {
							newDepartments.add(department);
						}
					});
		}
		
		for (Department newDepartment : newDepartments) {
			Employee employee = newDepartment.getEmployees().stream()
					.max(Comparator.comparing(Employee::getSalary)).get();
			System.out.printf("%s - %.2f%n",
					newDepartment.getName(),
					employee.getSalary());
		}
		
		this.manager.getTransaction().commit();
	}
}

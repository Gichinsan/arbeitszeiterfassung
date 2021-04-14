package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Employee;

import java.util.List;

public interface IEmployeeService {

    boolean save(Employee employee);

    List<Employee> getAllEmployee();

}

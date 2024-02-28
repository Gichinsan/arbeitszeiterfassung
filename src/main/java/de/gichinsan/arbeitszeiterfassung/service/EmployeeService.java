package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Employee;
import de.gichinsan.arbeitszeiterfassung.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public boolean save(Employee employee) {

        //Check before save
       // var exists = repository.existsById(employee.getId());
       // if (exists) {
            repository.save(employee);
            return true;
       // } else {
       //     return false;
       // }
    }

    @Override
    public boolean saveOrUpdate(Employee employee) {
        if (employee.getId() != null) {
            boolean exists = repository.existsById(employee.getId());
            repository.save(employee);
            return true;
        } else {

            repository.save(employee);
            return true;
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        return repository.findAll();
    }
}

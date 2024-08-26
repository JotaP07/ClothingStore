package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ValidationService validationService;

    public String save(Employee employee) {
        employeeRepository.save(employee);
        return "Funcionário(a) " + employee.getName() + " salvo(a) com sucesso!";
    }

    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public String update(Employee employee, Long id) {
        validationService.validateEmployeeById(id);
        employee.setId(id);
        employeeRepository.save(employee);
        return "Funcionário(a) " + employee.getName() + " atualizado(a) com sucesso!";
    }

    public String delete(Long id) {
        validationService.validateEmployeeById(id);
        employeeRepository.deleteById(id);
        return "Funcionário(a) com ID " + id + " deletado com sucesso!";
    }

    public Employee findById(Long id) {
        return validationService.validateEmployeeById(id);
    }

    public List<Employee> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        validationService.validateList(employees, "funcionário(a)");
        return employees;
    }

    public List<Employee> findByNameContaining(String name) {
        validationService.validateString(name, "name");
        List<Employee> employees = employeeRepository.findByNameContaining(name);
        validationService.validateList(employees, "funcionário(a)");
        return employees;
    }

    public List<Employee> findByRegistration(String registration) {
        validationService.validateRegistration(registration);
        List<Employee> employees = employeeRepository.findByRegistration(registration);
        validationService.validateList(employees, "funcionário(a) com o registro " + registration);
        return employees;
    }

    public List<Employee> findEmployeesWithSales() {
        List<Employee> employees = employeeRepository.findEmployeesWithSales();
        validationService.validateList(employees, "funcionário(a) com vendas realizadas");
        return employees;
    }
}

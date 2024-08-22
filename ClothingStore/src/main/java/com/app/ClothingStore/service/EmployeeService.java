package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String save(Employee employee) {
        employeeRepository.save(employee);
        return "Funcionário " + employee.getName() + " salvo com sucesso!";
    }

    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public String update(Employee employee, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            employeeRepository.save(employee);
            return "Funcionário " + employee.getName() + " atualizado com sucesso!";
        } else {
            throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!");
        }
    }

    public String delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Funcionário com ID " + id + " deletado com sucesso!";
        } else {
            throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!");
        }
    }

    public Employee findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!"));
    }

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Nenhum funcionário encontrado!");
        }
        return employees;
    }

    public List<Employee> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'name' é obrigatório e não pode estar vazio!");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("O parâmetro 'name' não pode ser um número!");
        }
        List<Employee> employees = employeeRepository.findByNameContaining(name);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Nenhum funcionário encontrado com o nome/letra \"" + name + "\"");
        }
        return employees;
    }

    public List<Employee> findByRegistration(String registration) {
        if (registration == null || registration.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'registration' é obrigatório e não pode estar vazio!");
        }
        List<Employee> employees = employeeRepository.findByRegistration(registration);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Nenhum funcionário encontrado com o registro \"" + registration + "\"");
        }
        return employees;
    }

    public List<Employee> findEmployeesWithSales() {
        List<Employee> employees = employeeRepository.findEmployeesWithSales();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Nenhum funcionário encontrado com vendas!");
        }
        return employees;
    }
}

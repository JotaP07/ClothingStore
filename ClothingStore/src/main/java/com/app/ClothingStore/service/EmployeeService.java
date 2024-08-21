package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.repository.EmployeeRepository;
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

    public void saveAll(List<Employee> employee){
        employeeRepository.saveAll(employee);
    }

    public String update(Employee employee, Long id) {
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            employeeRepository.save(employee);
            return "Funcionário " + employee.getName() + " atualizado com sucesso!";
        } else {
            return "Funcionário não encontrado!";
        }
    }

    public String delete(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Funcionário com ID " + id + " deletado com sucesso!";
        } else {
            return "Funcionário com ID " + id + " não encontrado!";
        }
    }

}

package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.repository.ClientRepository;
import com.app.ClothingStore.repository.EmployeeRepository;
import com.app.ClothingStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;


    //tlvz fazer dps validateID genérica
    public Client validateClientById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser um valor positivo!");
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado!"));
    }

    public Employee validateEmployeeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser um valor positivo!");
        }
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!"));
    }

    public Product validateProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do produto deve ser um valor positivo!");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado!"));
    }


    public <T> void validateList(List<T> list, String entityName) {
        if (list.isEmpty()) {
            throw new EntityNotFoundException("Nenhum(a) " + entityName + " encontrado(a)!");
        }
    }

    public void validateString(String name, String entityName) { //validar o name e o endereço do sale
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + entityName + "' é obrigatório e não pode estar vazio!");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("O parâmetro '" + entityName + "' não pode ser um número!");
        }
    }

    public void validateCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'cpf' é obrigatório e não pode estar vazio!");
        }
        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        if (!cpf.matches(cpfRegex)) {
            throw new IllegalArgumentException("O CPF fornecido não está no formato correto!");
        }
    }

    public void validateAge(Integer age) {
        if (age != null && age <= 0) {
            throw new IllegalArgumentException("A idade deve ser um valor positivo!");
        }
    }










    public void validateMinSpendingValue(Double minValue) {
        if (minValue == null) {
            throw new IllegalArgumentException("O valor mínimo deve ser informado!");
        }
        if (minValue <= 0) {
            throw new IllegalArgumentException("O valor mínimo deve ser maior que zero!");
        }
//        if (minValue.isNaN()) {
//            throw new IllegalArgumentException("O valor mínimo deve ser numérico!"); }
    }
}



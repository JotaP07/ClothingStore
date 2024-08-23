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

@Service
public class ValidationService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

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
}

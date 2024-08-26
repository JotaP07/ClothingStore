package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.entity.Sale;
import com.app.ClothingStore.repository.ClientRepository;
import com.app.ClothingStore.repository.EmployeeRepository;
import com.app.ClothingStore.repository.ProductRepository;
import com.app.ClothingStore.repository.SaleRepository;
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

    @Autowired
    private SaleRepository saleRepository;


    //tlvz fazer dps validateID genérica
    public Client validateClientById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do cliente não deve estar vazio e deve ser um valor positivo!");
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado!"));
    }

    public Employee validateEmployeeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do funcionário não deve estar vazio e deve ser deve ser um valor positivo!");
        }
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!"));
    }

    public Product validateProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do produto não deve estar vazio e deve ser um valor positivo!");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado!"));
    }

    public Sale validateSaleById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID da venda não deve estar vazio deve ser um valor positivo!");
        }
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID " + id + " não encontrada!"));
    }

    public void validateSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }
        if (sale.getClient() == null || sale.getClient().getId() == null) {
            throw new IllegalArgumentException("O cliente da venda não pode ser nulo e deve ter um ID válido!");
        }
        if (sale.getEmployee() == null || sale.getEmployee().getId() == null) {
            throw new IllegalArgumentException("O funcionário da venda não pode ser nulo e deve ter um ID válido!");
        }
        if (sale.getProducts() == null || sale.getProducts().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um produto associado!");
        }
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


    public void validateRegistration(String registration) {
        if (registration == null || registration.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'registration' é obrigatório e não pode estar vazio!");
        }
        try {
            int regValue = Integer.parseInt(registration.trim());
            if (regValue < 0) {
                throw new IllegalArgumentException("O registro deve ser um valor positivo!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'registration' deve ser um número inteiro válido!");
        }
    }

    public void validatePrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("O preço é obrigatório e não pode ser nulo!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("O preço não deve ser negativo!");
        }
//        if (price.isNaN()) { //funfa não
//            throw new IllegalArgumentException("O preço não pode ser 'NaN'!");
//        }

    }

    public void validatePriceInRange(Double minPrice, Double maxPrice) {

        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Os parâmetros 'minPrice' e 'maxPrice' são obrigatórios!");
        }
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Os parâmetros 'minPrice' e 'maxPrice' devem ser maiores ou iguais a zero!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("O parâmetro 'minPrice' deve ser menor ou igual ao parâmetro 'maxPrice'!");
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



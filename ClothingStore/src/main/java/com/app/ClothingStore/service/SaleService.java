package com.app.ClothingStore.service;

import com.app.ClothingStore.dto.ClientByMinSpendingDTO;
import com.app.ClothingStore.dto.ClientSpendingDTO;
import com.app.ClothingStore.dto.TopSellingProductsDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ValidationService validationService;

    private double totalCalculator(List<Product> products) {
        double totalValue = 0;
        for (Product p : products) {
            Product product = this.productService.findById(p.getId());
            totalValue += product.getPrice();
        }
        return Math.round(totalValue * 100.0) / 100.0; // Arredondando para duas casas decimais
    }


    //funções pra validar
//    private Client validateClient(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("O ID do cliente deve ser um valor positivo!");
//        }
//        return clientRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado!"));
//    }
//
//    private Employee validateEmployee(Long id) {
//        return employeeRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!"));
//    }
//
//    private Product validateProduct(Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado!"));
//    }

    public String save(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }

        Client client = validationService.validateClientById(sale.getClient().getId());
        sale.setClient(client);

        Employee employee = validationService.validateEmployeeById(sale.getEmployee().getId());
        sale.setEmployee(employee);

        List<Product> products = sale.getProducts().stream()
                .map(product -> validationService.validateProductById(product.getId()))
                .collect(Collectors.toList());
        sale.setProducts(products);

        double totalValue = totalCalculator(products);
        sale.setTotalValue(totalValue);

        saleRepository.save(sale);
        return "Venda realizada com sucesso!\n" + "Valor total: R$ " + totalValue;
    }

    public void saveAll(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            throw new IllegalArgumentException("A lista de vendas não pode ser nula ou vazia!");
        }

        for (Sale sale : sales) {
            if (sale == null) {
                throw new IllegalArgumentException("Venda não pode ser nula na lista!");
            }

            Client client = validationService.validateClientById(sale.getClient().getId());
            sale.setClient(client);

            Employee employee = validationService.validateEmployeeById(sale.getEmployee().getId());
            sale.setEmployee(employee);

            List<Product> products = sale.getProducts().stream()
                    .map(product -> validationService.validateProductById(product.getId()))
                    .collect(Collectors.toList());
            sale.setProducts(products);

            double totalValue = totalCalculator(products);
            sale.setTotalValue(totalValue);
        }

        saleRepository.saveAll(sales);
    }

    public String update(Sale sale, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um valor positivo!");
        }
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }

        Client client = validationService.validateClientById(sale.getClient().getId());
        sale.setClient(client);

        Employee employee = validationService.validateEmployeeById(sale.getEmployee().getId());
        sale.setEmployee(employee);

        List<Product> products = sale.getProducts().stream()
                .map(product -> validationService.validateProductById(product.getId()))
                .collect(Collectors.toList());
        sale.setProducts(products);

        if (saleRepository.existsById(id)) {
            sale.setId(id);
            double totalValue = totalCalculator(products);
            sale.setTotalValue(totalValue);

            saleRepository.save(sale);
            return "Venda com o ID " + sale.getId() + " atualizada com sucesso!\n" + "Valor total: R$ " + totalValue;
        } else {
            throw new EntityNotFoundException("Venda com o ID " + id + " não encontrada!");
        }
    }

    public String delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um valor positivo!");
        }
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return "Venda com ID " + id + " deletada com sucesso!";
        } else {
            throw new EntityNotFoundException("Venda com ID " + id + " não encontrada!");
        }
    }

    public Sale findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda com o ID " + id + " não encontrado!"));
    }

    public List<Sale> findAll() {
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma venda encontrada!");
        }
        return sales;
    }


    public List<Sale> findByAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'addres' é obrigatório e não deve estar vazia!");
        }
        if (address.matches("\\d+")) {
            throw new IllegalArgumentException("O parâmetro 'addres' não deve ser um número!");
        }
        List<Sale> sales = saleRepository.findByAddress(address);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma venda encontrada com o endereço: " + address);
        }
        return sales;
    }


    public Client findByClientId(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser um valor positivo!");
        }

        List<Sale> sales = saleRepository.findByClientId(clientId);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("Cliente com ID " + clientId + " não possui vendas!");
        }
        //retorna somente 1
        return sales.get(0).getClient();
    }

    public ClientSpendingDTO findTopClientBySpending() {
        List<ClientSpendingDTO> clients = saleRepository.findClientsOrderedBySpending();
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Nenhum cliente encontrado!");
        }
        return clients.get(0);
    }

    public List<TopSellingProductsDTO> findTopSellingProducts() {
        List<TopSellingProductsDTO> topProducts = saleRepository.findTopSellingProducts();
        if (topProducts.isEmpty()) {
             throw new EntityNotFoundException("Nenhum produto encontrado!");
        }
        return topProducts;
    }


    public List<Sale> findSalesByEmployeeId(Long employeeId) {
        return saleRepository.findSalesByEmployeeId(employeeId);
    }

    public List<ClientByMinSpendingDTO> findClientsByMinSpending(Double minValue) {
        return saleRepository.findClientsByMinSpending(minValue);
    }

}




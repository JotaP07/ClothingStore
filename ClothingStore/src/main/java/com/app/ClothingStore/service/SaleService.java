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

    private double totalCalculator(List<Product> products) {
        double totalValue = 0;
        for (Product p : products) {
            Product product = this.productService.findById(p.getId());
            totalValue += product.getPrice();
        }
        return totalValue;
    }
    
 //funções pra validar
    private Client validateClient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser um valor positivo!");
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado!"));
    }

    private Employee validateEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado!"));
    }

    private Product validateProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado!"));
    }

    public String save(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }

        Client client = validateClient(sale.getClient().getId());
        sale.setClient(client);

        Employee employee = validateEmployee(sale.getEmployee().getId());
        sale.setEmployee(employee);

        List<Product> products = sale.getProducts().stream()
                .map(product -> validateProduct(product.getId()))
                .collect(Collectors.toList());
        sale.setProducts(products);

        double totalValue = totalCalculator(products);
        sale.setTotalValue(totalValue);

        DecimalFormat value = new DecimalFormat("#.00");//formatar o double pra .00
        String formattedTotalValue = value.format(totalValue);

        saleRepository.save(sale);
        return "Venda realizada com sucesso!\n" + "Valor total: R$ " + formattedTotalValue;
    }

    public void saveAll(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            throw new IllegalArgumentException("A lista de vendas não pode ser nula ou vazia!");
        }

        for (Sale sale : sales) {
            if (sale == null) {
                throw new IllegalArgumentException("Venda não pode ser nula na lista!");
            }

            validateClient(sale.getClient().getId());
            validateEmployee(sale.getEmployee().getId());

            List<Product> products = sale.getProducts().stream()
                    .map(product -> validateProduct(product.getId()))
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

        validateClient(sale.getClient().getId());
        validateEmployee(sale.getEmployee().getId());

        List<Product> products = sale.getProducts().stream()
                .map(product -> validateProduct(product.getId()))
                .collect(Collectors.toList());
        sale.setProducts(products);

        if (saleRepository.existsById(id)) {
            sale.setId(id);
            double totalValue = totalCalculator(products);
            sale.setTotalValue(totalValue);

            DecimalFormat df = new DecimalFormat("#.00");
            String formattedTotalValue = df.format(totalValue);

            saleRepository.save(sale);
            return "Venda com o ID " + sale.getId() + " atualizada com sucesso!\n" + "Valor total: R$ " + formattedTotalValue;
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
}










//
//    public Client validClientById(Long id){
//        if (clientRepository.existsById(id)){
//            Client client = clientRepository.findById(id).get();
//            return client;
//        }
//        throw new RuntimeException("Cliente com o id "+ id + "não encontrado!");
//    }
//
//    public Employee validEmployeeById(Long id){
//        if (employeeRepository.existsById(id)){
//            Employee employee = employeeRepository.findById(id).get();
//            return employee;
//        }
//        throw new RuntimeException("Funcionário com o id "+ id + "não encontrado!");
//    }



    //       sale.setClient(validClientById(sale.getClient().getId()));
//       sale.setEmployee(validEmployeeById(sale.getEmployee().getId()));

//        if (sale == null) {
//            throw new IllegalArgumentException("A venda não pode ser nula!");
//        }
//        if (sale.getClient() == null || sale.getEmployee() == null || sale.getProducts() == null || sale.getProducts().isEmpty()) {
//            throw new IllegalArgumentException("A venda deve ter um cliente, um funcionário e pelo menos um produto associado!");
//        }
//        saleRepository.save(sale);
//        return "Venda com ID " + sale.getId() + " salva com sucesso!";


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public String save(Sale sale) {
        validationService.validateSale(sale);

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
            validationService.validateSale(sale);

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
        validationService.validateSaleById(id);
        validationService.validateSale(sale);

        Client client = validationService.validateClientById(sale.getClient().getId());
        sale.setClient(client);

        Employee employee = validationService.validateEmployeeById(sale.getEmployee().getId());
        sale.setEmployee(employee);

        List<Product> products = sale.getProducts().stream()
                .map(product -> validationService.validateProductById(product.getId()))
                .collect(Collectors.toList());
        sale.setProducts(products);

        validationService.validateSaleById(id);
        sale.setId(id);
        double totalValue = totalCalculator(products);
        sale.setTotalValue(totalValue);

        saleRepository.save(sale);
        return "Venda com o ID " + sale.getId() + " atualizada com sucesso!\n" + "Valor total: R$ " + totalValue;

    }

    public String delete(Long id) {
        validationService.validateSaleById(id);
        saleRepository.deleteById(id);
        return "Venda com ID " + id + " deletada com sucesso!";
    }

    public Sale findById(Long id) {
        return validationService.validateSaleById(id);
    }

    public List<Sale> findAll() {
        List<Sale> sales = saleRepository.findAll();
        validationService.validateList(sales, "venda");
        return sales;
    }

    public List<Sale> findByAddress(String address) {
        validationService.validateString(address, "address");
        List<Sale> sales = saleRepository.findByAddress(address);
        validationService.validateList(sales, "venda com endereço " + address);
        return sales;
    }

    public Client findByClientId(Long clientId) { //procurar a venda por id de cliente
        validationService.validateClientById(clientId);
        List<Sale> sales = saleRepository.findByClientId(clientId);
        validationService.validateList(sales, "cliente com ID " + clientId);
        return sales.get(0).getClient();//retorna somente 1
    }

    public ClientSpendingDTO findTopClientBySpending() { // caso queira listar mais de um transformar em LIST
        List<ClientSpendingDTO> clients = saleRepository.findClientsOrderedBySpending();
        validationService.validateList(clients, "cliente");
        return clients.get(0);
    }

    public List<TopSellingProductsDTO> findTopSellingProducts() {
        List<TopSellingProductsDTO> topProducts = saleRepository.findTopSellingProducts();
        validationService.validateList(topProducts, "produto vendido");
        return topProducts;
    }

    public List<Sale> findSalesByEmployeeId(Long employeeId) {
        validationService.validateEmployeeById(employeeId);
        List<Sale> employeeSales = saleRepository.findSalesByEmployeeId(employeeId);
        validationService.validateList(employeeSales, "venda realizada por funcionário(a)");
        return employeeSales;
    }

    public List<ClientByMinSpendingDTO> findClientsByMinSpending(Double minValue) {
        validationService.validateMinSpendingValue(minValue);
        List<ClientByMinSpendingDTO> clientsSales = saleRepository.findClientsByMinSpending(minValue);
        validationService.validateList(clientsSales, "cliente com valor de compra miníma " + minValue);
        return clientsSales;
    }
}




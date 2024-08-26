package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    public String save(Product product) {
        productRepository.save(product);
        return "Produto \"" + product.getName() + "\" salvo com sucesso!";
    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public String update(Product product, Long id) {
        validationService.validateProductById(id);
        product.setId(id);
        productRepository.save(product);
        return "Produto \"" + product.getName() + "\" atualizado com sucesso!";
    }

    public String delete(Long id) {
        validationService.validateProductById(id);
        productRepository.deleteById(id);
        return "Produto com ID " + id + " deletado com sucesso!";
    }

    public Product findById(Long id) {
        return validationService.validateProductById(id);
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        validationService.validateList(products, "produto");
        return products;
    }

    public List<Product> findByNameContaining(String name) {
        validationService.validateString(name, "name");
        List<Product> products = productRepository.findByNameContaining(name);
        validationService.validateList(products, "produto");
        return products;
    }

    public List<Product> findByPriceGreaterThan(Double price) {
        validationService.validatePrice(price);
        List<Product> products = productRepository.findByPriceGreaterThan(price);
        validationService.validateList(products, "produto com preço maior que " + price);
        return products;
    }

    public List<Product> findProductsInRange(Double minPrice, Double maxPrice) {
        validationService.validatePriceInRange(minPrice, maxPrice);
        List<Product> products = productRepository.findProductsInRange(minPrice, maxPrice);
        validationService.validateList(products, "produto entre os preços " + minPrice + " e " + maxPrice);
        return products;
    }
}

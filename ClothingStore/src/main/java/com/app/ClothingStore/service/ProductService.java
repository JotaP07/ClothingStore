package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String save(Product product) {
        productRepository.save(product);
        return "Produto \"" + product.getName() + "\" salvo com sucesso!";
    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public String update(Product product, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (productRepository.existsById(id)) {
            product.setId(id);
            productRepository.save(product);
            return "Produto \"" + product.getName() + "\" atualizado com sucesso!";
        } else {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado!");
        }
    }

    public String delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Produto com ID " + id + " deletado com sucesso!";
        } else {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado!");
        }
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto encontrado!");
        }
        return products;
    }

    public Product findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com o ID " + id + " não encontrado!"));
    }

    public List<Product> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'name' é obrigatório e não pode estar vazio!");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("O parâmetro 'name' não pode ser um número!");
        }
        List<Product> products = productRepository.findByNameContaining(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto encontrado com o nome: " + name);
        }
        return products;
    }

    public List<Product> findByPriceGreaterThan(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("O parâmetro 'price' é obrigatório e deve ser maior que zero!");
        }
        List<Product> products = productRepository.findByPriceGreaterThan(price);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto encontrado com preço maior que " + price);
        }
        return products;
    }

    public List<Product> findProductsInRange(Double minPrice, Double maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Os parâmetros 'minPrice' e 'maxPrice' são obrigatórios!");
        }
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Os parâmetros 'minPrice' e 'maxPrice' devem ser maiores ou iguais a zero!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("O 'minPrice' deve ser menor ou igual ao 'maxPrice'!");
        }
        List<Product> products = productRepository.findProductsInRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto encontrado no intervalo de preço de " + minPrice + " a " + maxPrice);
        }
        return products;
    }
}

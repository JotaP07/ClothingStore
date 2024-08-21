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

    public String save(Product product) {
        productRepository.save(product);
        return "Produto \"" + product.getName() + "\" salvo com sucesso!";
    }

    public void saveAll(List<Product> product){
        productRepository.saveAll(product);
    }

    public String update(Product product, Long id) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            productRepository.save(product);
            return "Produto \"" + product.getName() + "\" atualizado com sucesso!";
        } else {
            return "Produto não encontrado!";
        }
    }

    public String delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Produto com ID " + id + " deletado com sucesso!";
        } else {
            return "Produto com ID " + id + " não encontrado!";
        }
    }
}

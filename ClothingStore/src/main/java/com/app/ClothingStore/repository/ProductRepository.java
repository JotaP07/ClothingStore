package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByPriceGreaterThan(Double price);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    public List<Product> findProductsInRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}

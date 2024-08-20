package com.app.ClothingStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome nao deve estar vazio")
    private String name;

    @NotNull(message = "Deve conter um preço")
    @Positive(message = "O valor total deve ser maior que zero")
    private Double price;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("products")
    private List<Sale> sales;
}

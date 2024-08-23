package com.app.ClothingStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private double totalValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("sales")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties("sales")
    private Employee employee;

    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties("sales")
    private List<Product> products;
}

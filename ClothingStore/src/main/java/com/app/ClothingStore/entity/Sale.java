package com.app.ClothingStore.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Valor total é obrigatório")
    @Positive(message = "O valor total deve ser maior que zero")
    private Double totalValue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    @NotNull(message = "cliente não pode estar vazio")
    @JsonIgnoreProperties("sales")
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    @NotNull(message = "funcionario nao pode estar vazio")
    private Employee employee;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @NotEmpty(message = "é necessário selecionar ao menos 1 item")
    @JsonIgnoreProperties("sales")
    private List<Product> products;

}

package com.app.ClothingStore.entity;


import com.app.ClothingStore.service.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    private Double total_value;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "client_id")
//    @NotNull(message = "cliente não pode estar vazio")
//    @JsonIgnoreProperties("sales")
//    private Client client;
//
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "employee_id")
//    @NotNull(message = "funcionario nao pode estar vazio")
//    private Employee employee;

    @NotEmpty(message = "é necessário selecionar ao menos 1 item")
    private List<Product> products;

}

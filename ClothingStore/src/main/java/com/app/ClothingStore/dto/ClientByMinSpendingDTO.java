package com.app.ClothingStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientByMinSpendingDTO {
    private Long id;
    private String name;
    private String cpf;
    private Integer age;
    private String telephone;
    private Double totalSpendingOfPurchases;

}


//    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
//    @JsonIgnoreProperties("client")



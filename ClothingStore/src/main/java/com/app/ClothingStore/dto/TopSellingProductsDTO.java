package com.app.ClothingStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingProductsDTO {
    private Long id;
    private String name;
    private Double price;
    private Double totalSpending;

}

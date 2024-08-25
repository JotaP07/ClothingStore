package com.app.ClothingStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientSpendingDTO {
    private Long id;
    private String name;
    private Double totalSpending;

}

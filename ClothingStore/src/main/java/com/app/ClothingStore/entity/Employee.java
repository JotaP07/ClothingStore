package com.app.ClothingStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo!")
    @Pattern(regexp = "^(\\S+\\s+\\S+.*)$", message = "O nome deve conter pelo menos duas palavras!")
    private String name;

    @NotNull(message = "A idade não pode ser nula!")
    @Min(value = 0, message = "A idade não deve ser negativa!")
    private Integer age;

    @NotBlank(message = "O registro não pode ser nulo!")
    private String registration;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("employee")
    private List<Sale> sales;

}

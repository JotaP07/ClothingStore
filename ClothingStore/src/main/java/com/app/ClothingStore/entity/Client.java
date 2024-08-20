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
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo.")
    @Pattern(regexp = "^(\\S+\\s+\\S+.*)$", message = "O nome deve conter pelo menos duas palavras.")
    private String name;

    @NotNull(message = "O CPF não deve ser nulo.")
    @CPF(message = "CPF Inválido. O formato deve ser 123.456.789.09")
    private String cpf;

    @NotNull(message = "A idade não pode ser nula.")
    @Min(value = 0, message = "A idade não deve ser negativa.")
    private Integer age;

    @NotNull(message = "O telefone não deve ser nulo.")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Número de telefone inválido. O formato deve ser (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
    private String telephone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("client")
    private List<Sale> sales;
}

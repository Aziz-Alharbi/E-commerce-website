package com.example.capstone_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 3)
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double price;

    @NotEmpty
    @Column(columnDefinition = "varchar(30) not null")
    private Integer categoryID;

    @NotEmpty
    @Column(columnDefinition = "varchar(30) not null")
    private String category;

    @NotNull
    @Column(nullable = false)
    private Double discountPercentage;

    @NotNull
    @Column(nullable = false)
    private Double deliveryFee;
}

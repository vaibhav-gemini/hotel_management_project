package com.example.hotel_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="customer_details")
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;
    @Column(name = "customer_name")
    @NotNull(message = "Customer name cannot be null/empty")
    private String customer_name;
    @Column(name = "customer_address")
    @NotNull(message = "Customer address cannot be null/empty")
    private String customer_address;
    @Column(name = "customer_number")
    @NotNull(message = "Customer number cannot be null/empty")
    private String customer_number;
    @Column(name = "customer_age")
    @NotNull(message = "Customer age cannot be null/empty")
    private int customer_age;

}

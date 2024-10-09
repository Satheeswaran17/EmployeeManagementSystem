package com.e5.employeemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * It is class represent the Laptop.
 * </p>
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private String brand;
    private String os;
    private boolean isDeleted;
}

package com.avtopark.avtopark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String brand;
    
    @Column(nullable = false, length = 50)
    private String model;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false)
    private Integer mileage;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    // One-to-One with CarDetail
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_details_id", nullable = false)
    private CarDetail carDetail;
    
    // One-to-Many with Rental
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rental> rentals;
    
    // Many-to-Many with Service
    @ManyToMany
    @JoinTable(
        name = "car_service",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;
    
    // One-to-Many with CarImage
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarImage> images;
}
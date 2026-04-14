package com.avtopark.avtopark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    // Many-to-Many with Car
    @ManyToMany(mappedBy = "services")
    private Set<Car> cars;
}
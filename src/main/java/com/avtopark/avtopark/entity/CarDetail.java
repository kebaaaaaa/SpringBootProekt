package com.avtopark.avtopark.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 20)
    private String color;
    
    private Integer seatingCapacity;
    
    @Column(length = 20)
    private String fuelType;
}
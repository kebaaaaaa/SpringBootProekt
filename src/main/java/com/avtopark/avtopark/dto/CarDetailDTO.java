package com.avtopark.avtopark.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDetailDTO {
    private Long id;
    private String description;
    private String color;
    private Integer seatingCapacity;
    private String fuelType;
}
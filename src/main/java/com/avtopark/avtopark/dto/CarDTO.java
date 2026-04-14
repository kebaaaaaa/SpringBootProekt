package com.avtopark.avtopark.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private CarDetailDTO carDetail;
}
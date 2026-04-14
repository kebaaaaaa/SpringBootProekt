package com.avtopark.avtopark.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarImageDTO {
    private Long id;
    private Long carId;
    private String fileName;
    private String fileType;
    private byte[] fileContent;
}
package com.avtopark.avtopark.mapper;

import com.avtopark.avtopark.dto.CarDTO;
import com.avtopark.avtopark.dto.CarDetailDTO;
import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.CarDetail;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDto(Car car);

    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "images", ignore = true)
    Car toEntity(CarDTO carDTO);
    
    CarDetailDTO toDto(CarDetail carDetail);
    
    CarDetail toEntity(CarDetailDTO carDetailDTO);
}
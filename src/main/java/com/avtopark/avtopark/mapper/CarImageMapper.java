package com.avtopark.avtopark.mapper;

import com.avtopark.avtopark.dto.CarImageDTO;
import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.CarImage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarImageMapper {
    @Mapping(source = "car.id", target = "carId")
    CarImageDTO toDto(CarImage carImage);

    @Mapping(source = "carId", target = "car")
    CarImage toEntity(CarImageDTO carImageDTO);

    default Car mapCar(Long carId) {
        if (carId == null) {
            return null;
        }
        Car car = new Car();
        car.setId(carId);
        return car;
    }
}
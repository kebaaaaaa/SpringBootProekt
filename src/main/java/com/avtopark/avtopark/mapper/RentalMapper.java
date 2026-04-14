package com.avtopark.avtopark.mapper;

import com.avtopark.avtopark.dto.RentalDTO;
import com.avtopark.avtopark.entity.Car;
import com.avtopark.avtopark.entity.Rental;
import com.avtopark.avtopark.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "car.id", target = "carId")
    RentalDTO toDto(Rental rental);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "carId", target = "car")
    Rental toEntity(RentalDTO rentalDTO);

    default User mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Car mapCar(Long carId) {
        if (carId == null) {
            return null;
        }
        Car car = new Car();
        car.setId(carId);
        return car;
    }
}
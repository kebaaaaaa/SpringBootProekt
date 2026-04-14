package com.avtopark.avtopark.mapper;

import com.avtopark.avtopark.dto.ServiceDTO;
import com.avtopark.avtopark.entity.Service;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    
    @Mapping(source = "price", target = "price")
    ServiceDTO toDto(Service service);
    
    @Mapping(source = "price", target = "price")
    @Mapping(target = "cars", ignore = true)
    Service toEntity(ServiceDTO serviceDTO);
}
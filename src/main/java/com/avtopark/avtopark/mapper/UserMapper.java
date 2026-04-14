package com.avtopark.avtopark.mapper;

import com.avtopark.avtopark.dto.UserDTO;
import com.avtopark.avtopark.entity.Role;
import com.avtopark.avtopark.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "roleName")
    UserDTO toDto(User user);

    @Mapping(source = "roleName", target = "role")
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDTO userDTO);

    default Role mapRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return null;
        }
        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
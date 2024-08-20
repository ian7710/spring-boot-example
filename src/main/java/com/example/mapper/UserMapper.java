package com.example.mapper;

import com.example.dto.UserDTO;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "department", source = "department")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "projects", source = "projects")
    UserDTO toUserDTO(User user);

    @Mapping(target = "department", source = "department")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "projects", source = "projects")
    User toUser(UserDTO userDTO);
}

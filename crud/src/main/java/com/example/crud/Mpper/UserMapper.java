package com.example.crud.Mpper;

import com.example.crud.DTO.UserDTO;
import com.example.crud.DTO.UserRequest;
import com.example.crud.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CarMapper.class, CourseMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target = "cars", source = "cars"),
            @Mapping(target = "courses", source = "courses")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target = "cars", source = "cars"),
            @Mapping(target = "courses", source = "courses")
    })
    User userDTOToUser(UserDTO userDTO);

    User userRequestToUser(UserRequest userRequest);

    UserRequest userToUserRequest(User user);
}

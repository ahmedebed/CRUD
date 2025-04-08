package com.example.crud.DTO;

import com.example.crud.Entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private List<CarDTO> cars;
    private List<CourseDTO > courses;
}

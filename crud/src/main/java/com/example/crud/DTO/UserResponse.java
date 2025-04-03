package com.example.crud.DTO;

import com.example.crud.Entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private Role role;
}

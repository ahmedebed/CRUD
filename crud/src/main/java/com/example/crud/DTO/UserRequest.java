package com.example.crud.DTO;

import com.example.crud.Entity.Role;
import com.example.crud.Validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Builder
public class UserRequest {
    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @UniqueEmail(message = "Email is already taken")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private Role role;
}

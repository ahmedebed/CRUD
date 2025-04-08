package com.example.crud.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {
    private Long id;
    @NotBlank(message = "Car name must not be blank")
    private String name;
}

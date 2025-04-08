package com.example.crud.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    @NotBlank(message = "Course name must not be blank")
    private String name;
}

package com.example.crud.Controller;

import com.example.crud.DTO.CourseDTO;
import com.example.crud.DTO.UserDTO;
import com.example.crud.DTO.UserRequest;
import com.example.crud.Service.AdminService;
import com.example.crud.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin-api/users")
@RestController
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final AdminService adminService;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<UserDTO> users = adminService.getAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserRequest userRequest) {
        UserDTO userDTO = adminService.addUser(userRequest).getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest
    ) {
        return adminService.updateUser(id, userRequest);
    }
    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestBody CourseDTO courseDTO) {
        courseService.addCourse(courseDTO);
        return ResponseEntity.ok("Course added successfully");
    }
}

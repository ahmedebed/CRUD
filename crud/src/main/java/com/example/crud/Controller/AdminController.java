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
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserDTO> users = adminService.getAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok("User Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok(adminService.addUser(userRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding user");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest
    ) {
        try {
            return adminService.updateUser(id, userRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user");
        }
    }
    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestBody CourseDTO courseDTO){
        courseService.addCourse(courseDTO);
        return ResponseEntity.ok("Courses added success");
    }



}

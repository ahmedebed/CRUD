package com.example.crud.Controller;

import com.example.crud.DTO.UserDTO;
import com.example.crud.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
   // @PreAuthorize("hasRole('ADMIN')") // Only admin can access
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserDTO> users = userService.getAll(); // Pass token to service
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')") // Only admin can delete users
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id); // Pass token to service
            return ResponseEntity.ok("User Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") // Only admin can add users
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.addUser(userDTO)); // Pass token to service
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding user");
        }
    }

    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')") // Only admin can update users
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(userDTO)); // Pass token to service
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user");
        }
    }
}

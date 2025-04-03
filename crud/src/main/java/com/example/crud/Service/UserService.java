package com.example.crud.Service;

import com.example.crud.DTO.UserDTO;
import com.example.crud.Entity.Role;
import com.example.crud.Entity.User;
import com.example.crud.Repo.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated // Enable validation for service methods
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService; // Inject JwtService to verify token

    // Get all users
    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    // Add a new user
    public ResponseEntity<UserDTO> addUser(UserDTO userDTO) {
        // Check if the user already exists
        Optional<User> existingUser = userRepo.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Create and save the new user
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER); // Default role for the new user
        userRepo.save(user);

        userDTO.setId(user.getId()); // Set the ID to the UserDTO

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO); // Respond with 201 status on successful creation
    }

    // Update an existing user
    public ResponseEntity<String> updateUser(@Valid UserDTO userDTO) {
        Optional<User> existingUser = userRepo.findById(userDTO.getId());

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(userDTO.getEmail());
            updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepo.save(updatedUser);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

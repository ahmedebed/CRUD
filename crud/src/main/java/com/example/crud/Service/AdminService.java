package com.example.crud.Service;

import com.example.crud.DTO.CarDTO;
import com.example.crud.DTO.CourseDTO;
import com.example.crud.DTO.UserDTO;
import com.example.crud.DTO.UserRequest;
import com.example.crud.Entity.Role;
import com.example.crud.Entity.User;
import com.example.crud.Mpper.UserMapper;
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
@Validated
public class AdminService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepo userRepo;

    // Get all users
    public List<UserDTO> getAll() {
        List<User> users = userRepo.findByRole(Role.USER);
        return users.stream().map(userMapper::userToUserDTO).toList();
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
    public ResponseEntity<UserDTO> addUser(UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    // Update an existing user
    public ResponseEntity<String> updateUser(Long id, @Valid UserRequest userRequest) {
        Optional<User> existingUserOpt = userRepo.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User existingUser = existingUserOpt.get();
        if (existingUser.getRole() == Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot update an admin user");
        }
        User updatedUser = userMapper.userRequestToUser(userRequest);
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.setRole(Role.USER);
        updatedUser.setCars(existingUser.getCars());
        updatedUser.setCourses(existingUser.getCourses());
        userRepo.save(updatedUser);
        return ResponseEntity.ok("User updated successfully");
    }


}

package com.example.crud.Service;

import com.example.crud.DTO.UserDTO;
import com.example.crud.Entity.Role;
import com.example.crud.Entity.User;
import com.example.crud.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService; // Inject JwtService to verify token

    public List<UserDTO> getAll(String token) {
        // Verify the token
        String username = jwtService.extractUsername(token);
        if (username == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }

        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }

    public void deleteUser(Long id, String token) {
        // Verify the token
        String username = jwtService.extractUsername(token);
        if (username == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }

        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public ResponseEntity<UserDTO> addUser(UserDTO userDTO, String token) {
        // Verify the token
        String username = jwtService.extractUsername(token);
        if (username == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER); // Default role is USER
        userRepo.save(user);

        userDTO.setId(user.getId());
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<String> updateUser(UserDTO userDTO, String token) {
        // Verify the token
        String username = jwtService.extractUsername(token);
        if (username == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }

        Optional<User> existingUser = userRepo.findById(userDTO.getId());

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(userDTO.getEmail());
            updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Update password as well
            userRepo.save(updatedUser);
            return ResponseEntity.ok("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

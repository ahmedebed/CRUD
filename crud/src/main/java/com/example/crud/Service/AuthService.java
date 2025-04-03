package com.example.crud.Service;

import com.example.crud.Exception.ResourceNotFoundException;
import com.example.crud.DTO.UserRequest;
import com.example.crud.Entity.Role;
import com.example.crud.Entity.User;
import com.example.crud.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(UserRequest userRequest) {
        if (userRepo.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setRole(Role.ADMIN);

        return userRepo.save(newUser);
    }
    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
}

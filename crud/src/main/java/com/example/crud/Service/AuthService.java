package com.example.crud.Service;

import com.example.crud.DTO.ResourceNotFoundException;
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
    public User register(User user){
        if(userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        return userRepo.save(user);

    }
    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
}

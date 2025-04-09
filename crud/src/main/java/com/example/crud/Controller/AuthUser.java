package com.example.crud.Controller;

import com.example.crud.DTO.LoginRequest;
import com.example.crud.DTO.UserRequest;
import com.example.crud.Entity.User;
import com.example.crud.Repo.UserRepo;
import com.example.crud.Service.AuthService;
import com.example.crud.Service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUser {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest userRequest) {
        User newUser = authService.register(userRequest);
        return ResponseEntity.ok(newUser);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        UserDetails userDetails = authService.getUserByEmail(loginRequest.getEmail());
        Optional<User> user =userRepo.findByEmail(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails,user.get().getId());
        return ResponseEntity.ok(token);
    }

}

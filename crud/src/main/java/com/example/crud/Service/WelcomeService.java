package com.example.crud.Service;

import com.example.crud.Exception.ResourceNotFoundException;
import com.example.crud.Entity.Role;
import com.example.crud.Entity.User;

import com.example.crud.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WelcomeService {

    private final UserRepo userRepo;

    public String getWelcomeMessageForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        if (user.getRole() == Role.ADMIN) {
            return "Welcome Admin " + user.getEmail();
        } else {
            return "Welcome User " + user.getEmail();
        }
    }
}
package com.example.crud.Service;

import com.example.crud.DTO.ResourceNotFoundException;
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

    public String getWelcomeMessageForUser(Long userId) {
        // Get target user
        User targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        boolean isSameUser = authentication.getName().equals(targetUser.getEmail());

        if (!isSameUser && !isAdmin) {
            throw new RuntimeException("You are not authorized to view this user's welcome message");
        }
        if (targetUser.getRole() == Role.ADMIN) {
            return "Welcome Admin " + targetUser.getEmail();
        } else {
            return "Welcome User " + targetUser.getEmail();
        }
    }
}
package com.example.crud.Service;

import com.example.crud.DTO.CourseDTO;
import com.example.crud.Entity.Course;
import com.example.crud.Entity.User;
import com.example.crud.Repo.CourseRepo;
import com.example.crud.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;

    public ResponseEntity<String> addCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        courseRepo.save(course);

        return ResponseEntity.ok("Course added successfully");
    }

    public String EnrolledCourse(Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        User user = userRepo.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loggedInUserEmail));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        user.getCourses().add(course);
        userRepo.save(user);

        return "User " + loggedInUserEmail + " enrolled in course " + courseId;
    }
}

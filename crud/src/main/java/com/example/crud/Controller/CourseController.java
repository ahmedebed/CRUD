package com.example.crud.Controller;

import com.example.crud.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-api/course")
public class CourseController {
    private final CourseService courseService;
    @PostMapping("/{courseId}")
    public ResponseEntity<String> EnrolledCourse( @PathVariable Long courseId) {
        String responseMessage = courseService.EnrolledCourse(courseId);
        return ResponseEntity.ok(responseMessage);
    }
}


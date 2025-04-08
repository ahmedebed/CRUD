package com.example.crud.Mpper;

import com.example.crud.DTO.CourseDTO;
import com.example.crud.Entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO courseToCourseDTO(Course course);

    Course courseDTOToCourse(CourseDTO courseDTO);
}

package com.app.services;

import java.util.List;

import com.app.dtos.AddCourseDto;
import com.app.dtos.UserCoursesDto;
import com.app.pojos.Course;

public interface CourseService {
	List<UserCoursesDto> getAllCourses();
	List<UserCoursesDto> getAllCourses(Long userid);
	Course getCourseById(Long courseId);
	Course addCourse(AddCourseDto addCourseDto);
	int getUserCountByCourseId(Long courseId);
}
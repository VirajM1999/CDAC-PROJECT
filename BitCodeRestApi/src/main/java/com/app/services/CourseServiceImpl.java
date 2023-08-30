package com.app.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dtos.AddCourseDto;
import com.app.dtos.UserCoursesDto;
import com.app.pojos.Course;
import com.app.pojos.Order;
import com.app.pojos.User;
import com.app.repositories.CourseRepository;
import com.app.repositories.OrderRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<UserCoursesDto> getAllCourses() {
		List<Course> listCourses = courseRepo.findAll();
		List<UserCoursesDto> userCoursesList = new ArrayList<UserCoursesDto>();
		for(Course c : listCourses) {
			UserCoursesDto userCourse = mapper.map(c, UserCoursesDto.class);
			userCoursesList.add(userCourse);
		}
		return userCoursesList;
	}
	
	@Override
	public List<UserCoursesDto> getAllCourses(Long userId) {
		User existingUser = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
		List<Course> courseList = courseRepo.findAll();
		List<UserCoursesDto> userCoursesList = new ArrayList<UserCoursesDto>();
		
		for(Course c : courseList) {
			UserCoursesDto userCourse = mapper.map(c, UserCoursesDto.class);
			if(existingUser.getUserCourses().contains(c)) {
				Order order = orderRepo.findByIdAndUser(c.getId(), existingUser).orElseThrow(() -> new RuntimeException("Order Not Found"));
				userCourse.setOrderStatus(order.getOrderStatus());
			}
			else {
				userCourse.setOrderStatus(null);
			}
			userCoursesList.add(userCourse);
		}
		return userCoursesList;
	}

	@Override
	public Course getCourseById(Long courseId) {
		if (courseRepo.existsById(courseId)) {
			Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
			return course;
		}
		return null;
	}

	@Override
	public Course addCourse(AddCourseDto addCourseDto) {
		Course newCourse = mapper.map(addCourseDto, Course.class);
		courseRepo.save(newCourse);
		return newCourse;
	}

	@Override
	public int getUserCountByCourseId(Long courseId) {
		Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
		return course.countOfUsers();
	}
}

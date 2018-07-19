package com.ktws.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.Course;


public interface CourseDao extends JpaRepository<Course, Long> {
	
	@Query("select count(*) from course c where c.course_name=?1")
	int findCountByCoursename(String coursename);
	
	@Query("select c from course c where c.course_name=?1")
	List<Course> findIdByCoursename(String tmpcoursename);
	
	public Course save(Course course);
	
	
}

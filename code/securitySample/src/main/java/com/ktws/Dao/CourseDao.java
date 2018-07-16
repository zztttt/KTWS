package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktws.Entity.Course;


public interface CourseDao extends JpaRepository<Course, Long> {

}

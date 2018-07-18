package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.Photo;

public interface PhotoDao extends JpaRepository<Photo, Long> {
	
	@Query("select p.url from photo p where p.course_id = ?1")
	String findUrlByCourseid(int id);
}

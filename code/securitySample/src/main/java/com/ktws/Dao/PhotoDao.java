package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.Photo;

public interface PhotoDao extends JpaRepository<Photo, Long> {
	
	Photo findById(int id);
	
	@Query("select avg(p.concentration/p.total) from photo p where course_id=?1")
	double getConcentration(int course_id);
	
	@Query("select avg(p.total/?1) from photo p where course_id=?2")
	double getAttendance(int should, int course_id);
	
}

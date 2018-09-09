package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.Classroom;

public interface ClassroomDao extends JpaRepository<Classroom, Long> {

	@Query("select cr from classroom cr where cr.id=?1")
	Classroom findById(int parseInt);

}

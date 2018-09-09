package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktws.Entity.Classroom;

public interface ClassroomDao extends JpaRepository<Classroom, Long> {

}

package com.ktws.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktws.Entity.Photo;

public interface PhotoDao extends JpaRepository<Photo, Long> {

}

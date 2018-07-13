package com.ktws.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.MyUser;

public interface UserDao extends JpaRepository<MyUser, Long> {

	List<MyUser> findAll();

	@Query("select u from user u where u.username=?1 and u.password=?2")
	MyUser findUser(String username, String password);
	
	@Query("select u from user u where u.username=?1")
	MyUser findByName(String username);
}

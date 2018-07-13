package com.ktws.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktws.Entity.User;

public interface UserDao extends JpaRepository<User, Long> {

	List<User> findAll();

	@Query("select u from user u where u.username=?1 and u.password=?2")
	User findUser(String username, String password);
	
	@Query("select u from user u where u.username=?1")
	User findByName(String username);
}

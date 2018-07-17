package com.ktws.Entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name="user")
public class User implements Serializable{
	private static final long serialVersionUID = 3576480021719469814L;
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String password;
	private String role;
	private String username;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="user")  
	private Set<Course> courseSet = new HashSet<Course>();
	
	public Set<Course> getCourseSet() {
		return courseSet;
	}

	public void setCourseSet(Set<Course> courseSet) {
		this.courseSet = courseSet;
	}
	
	public void addCourse(Course course){  
		course.setUser(this); //因为course是关系维护端 
        this.courseSet.add(course);  
    }
	
	public void setId(int id) {
    	this.id = id;
    }
    public int getId() {
    	return id;
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}

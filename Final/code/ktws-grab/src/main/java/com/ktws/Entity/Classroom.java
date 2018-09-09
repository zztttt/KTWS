package com.ktws.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="classroom")
public class Classroom {
	
	@Id  
	@Column(name="classroom_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String location;
	private int shot_interval;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="classroom")  
	private Set<Course> courseSet = new HashSet<Course>();
	
	public Set<Course> getCourseSet() {
		return courseSet;
	}

	public void setCourseSet(Set<Course> courseSet) {
		this.courseSet = courseSet;
	}
	
	public void addCourse(Course course){  
		course.setClassroom(this); //因为course是关系维护端 
        this.courseSet.add(course);  
    }
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getShot_interval() {
		return shot_interval;
	}
	public void setShot_interval(int shot_interval) {
		this.shot_interval = shot_interval;
	}
	
}

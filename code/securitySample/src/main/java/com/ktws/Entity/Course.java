package com.ktws.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="course")
public class Course {

	@Id  
	@Column(name="course_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="classroom_id") //指定外键的名称。外键一般都是在关系维护端定义  
	private Classroom classroom;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="user_id") //指定外键的名称。外键一般都是在关系维护端定义  //这个外键的名字似乎不太好
	private User user;
	
	private String course_name;
	private int total;
	private String time;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="course",fetch=FetchType.EAGER)  
	private Set<Photo> photoSet = new HashSet<Photo>();
	
	public Set<Photo> getPhotoSet() {
		return photoSet;
	}

	public void setPhotoSet(Set<Photo> photoSet) {
		this.photoSet = photoSet;
	}
	
	public void addPhoto(Photo photo){  
		photo.setCourse(this); //因为course是关系维护端 
        this.photoSet.add(photo);  
    }
	
	public String getCoursename() {
		return course_name;
	}
	public void setCoursename(String course_name) {
		this.course_name = course_name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Classroom getClassroom() {
		return classroom;
	}
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

package com.ktws.Entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="photo")
public class Photo {
	
	@Id
	@Column(name="photo_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String url;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="course_id") //指定外键的名称。外键一般都是在关系维护端定义  
	private Course course;
	
	private Date date;  // 日期类型?
	private int total;
	private int concentration;
	
	public int getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int concentration) {
		this.concentration = concentration;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}

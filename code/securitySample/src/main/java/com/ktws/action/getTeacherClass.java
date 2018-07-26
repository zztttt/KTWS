package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.UserDao;
import com.ktws.Entity.Classroom;
import com.ktws.Entity.Course;
import com.ktws.Entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class getTeacherClass extends HttpServlet{

	private static final long serialVersionUID = 1116288354617290640L;
	
	@PersistenceContext
	protected EntityManager em; 
	
	@Autowired
	protected UserDao userdao;
	
	public getTeacherClass() {
		super();
	}
	
	@RequestMapping(value="/getclasses",method=RequestMethod.GET)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			PrintWriter out = response.getWriter();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();

			String userName = userDetails.getUsername();
			System.out.println("user:"+userName);
			
			JSONArray jsonArray = new JSONArray();
			
			User user = userdao.findByName(userName);
			if (user == null) {
				System.out.println("user null");
				return;
			}
			Set<Course> courseSet = user.getCourseSet();
			if(courseSet.size() == 0) {
				System.out.println("set size == 0");
				return;
			}
			for (Course course:courseSet) {
				JSONObject jsonO = new JSONObject();
				jsonO.put("id", course.getId());
				jsonO.put("classname", course.getCoursename());
				jsonO.put("num", course.getTotal());
				
				//System.out.println(course.getTime() +" xx "+course.getTotal());
				Classroom classroom = course.getClassroom();
				//System.out.println("intval:"+classroom.getShot_interval());
				
				jsonO.put("frequency", classroom.getShot_interval());
				jsonO.put("open", "Y");
				
				jsonArray.add(jsonO);
			}
			//System.out.println(jsonArray.toString());
			//System.out.println(user.getUsername()+user.getPassword()+user.getId());
			out.println(jsonArray.toString());	
			out.flush();
			out.close();
		} catch (Exception ex) {
			if (ServletException.class.isInstance(ex)) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		}
	}
	
	
}

package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.CourseDao;
import com.ktws.Dao.UserDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.User;

@RestController
public class adminDeleteCourseInfo extends HttpServlet{
	
	private static final long serialVersionUID = 5515783093860956890L;
	
	@Autowired
	UserDao userdao;
	@Autowired 
	CourseDao coursedao;
	public adminDeleteCourseInfo() {
		super();
	}
	@RequestMapping(value="/admindeletecourseinfo",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");    
            
			String courseId = request.getParameter("course_id");
			System.out.println("courseId: "+ courseId);
			Course c = coursedao.findByCourseId(Integer.parseInt(courseId));
			Boolean isValid  = false;
			if( c != null) {
				coursedao.delete(c);
				isValid = true;
			}
            out.print(isValid);
            out.flush();
            out.close();
            
		}
		catch(Exception ex) {
			
		}
	}
}
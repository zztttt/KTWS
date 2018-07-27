package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.CourseDao;
import com.ktws.Entity.Course;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class adminGetCourseInfo extends HttpServlet{

	private static final long serialVersionUID = -3839182568051749616L;
	@Autowired
	CourseDao coursedao;
	
	public adminGetCourseInfo() {
		super();
	}
	@RequestMapping(value="/admingetcourseinfo",method=RequestMethod.GET)
	protected void doGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doGET");
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            
			List<Course> courses = coursedao.findAll();
			System.out.println(courses);
			
			JSONArray jsonArray = new JSONArray();
			int i = 1;
			for( Course course:courses ) {
				//System.out.println(user);
				JSONObject o = new JSONObject();
				o.put("key", i);
				o.put("course_id", course.getId());
				o.put("course_name", course.getCoursename());
				o.put("classroom_id", course.getClassroom().getId());
				o.put("username", course.getUser().getUsername());
				o.put("total", course.getTotal());
				o.put("time", course.getTime());
				jsonArray.add(o);
				i++;
			}
			out.println(jsonArray.toString());	
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			
		}
	}
}
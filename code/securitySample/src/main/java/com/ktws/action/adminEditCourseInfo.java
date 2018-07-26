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

import com.ktws.Dao.ClassroomDao;
import com.ktws.Dao.CourseDao;
import com.ktws.Dao.UserDao;
import com.ktws.Entity.Classroom;
import com.ktws.Entity.Course;
import com.ktws.Entity.User;

@RestController
public class adminEditCourseInfo extends HttpServlet{

	private static final long serialVersionUID = 5357871544557760902L;
	@Autowired
	CourseDao coursedao;
	@Autowired
	ClassroomDao classroomdao;
	@Autowired
	UserDao userdao;

	public adminEditCourseInfo() {
		super();
	}
	@RequestMapping(value="/admineditcourseinfo",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPOST");
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");

            String courseId = request.getParameter("course_id");
			String username = request.getParameter("username");
			String classroom_id = request.getParameter("classroom_id");
			String total = request.getParameter("total");
			String time = request.getParameter("time");
			
			System.out.printf("course_id:%s, username:%s, classroom_id:%s, total: %s, time: %s " ,
								courseId, username, classroom_id, total , time);
			Course course = coursedao.findByCourseId(Integer.parseInt(courseId));
			Classroom newClassroom = classroomdao.findById(Integer.parseInt(classroom_id));
			User newUser = userdao.findByName(username);
			if(newUser == null) {
				System.out.println("user not exists");
				out.print("user not exists");
	            out.flush();
	            out.close();
				return;
			}
			if(newClassroom == null) {
				System.out.println("classroom not exists");
				out.print("classroom not exists");
	            out.flush();
	            out.close();
				return;
			}
			course.setUser(newUser);
			course.setClassroom(newClassroom);
			course.setTotal(Integer.parseInt(total));
			course.setTime(time);
			coursedao.save(course);
            out.print(true);
            out.flush();
            out.close();
		}
		catch(Exception ex) {
			
		}
	}
}
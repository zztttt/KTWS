package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.CourseDao;
import com.ktws.Dao.PhotoDao;
import com.ktws.Dao.UserDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;
import com.ktws.Entity.User;

@RestController
public class adminEditUserInfo extends HttpServlet{

	@Autowired
	UserDao userdao;
	private static final long serialVersionUID = 3362753584586661630L;
	public adminEditUserInfo() {
		super();
	}
	@RequestMapping(value="/adminedituserinfo",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPOST");
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			int userId = Integer.parseInt(request.getParameter("user_id"));
			
			System.out.printf("username:%s, password:%s, user_id:%d " , username, password, userId);
			
			User u = userdao.findByUserId(userId);
			if(u != null) {
				u.setUsername(username);
				u.setPassword(password);
				userdao.save(u);
			}else {
				System.out.println("user not exist");
			}
			

            out.print(true);
            out.flush();
            out.close();
            
		}
		catch(Exception ex) {
			
		}
	}
}
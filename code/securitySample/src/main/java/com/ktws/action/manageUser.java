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
import com.ktws.Dao.PhotoDao;
import com.ktws.Dao.UserDao;
import com.ktws.Entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class manageUser extends HttpServlet{

	private static final long serialVersionUID = -5860947855984241435L;
	
	@Autowired
	CourseDao coursedao;
	@Autowired
	UserDao userdao;
	
	public manageUser() {
		super();
	}
	@RequestMapping(value="/manageruser",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPOST");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			
			String method = request.getParameter("method");
			String username = request.getParameter("username");
			System.out.println("method: " + method);
			System.out.println("username: "+username);
			
			if(method.equals("delete")) {
				User u = userdao.findByName(username);
				userdao.delete(u);
				System.out.println("finish delete user");
				out.print(true);
				out.flush();
		        out.close();
		        return;
			}else if(method.equals("add")) {
				String password = request.getParameter("password");
				System.out.print("password: "+password);
				User u = new User();
				u.setUsername(username);
				u.setPassword(password);
				u.setRole("ROLE_USER");
				userdao.save(u);
				System.out.println("finish add user");
				out.print(true);
				out.flush();
		        out.close();
		        return;
			}else {
				System.out.print("method does not exist");
				out.print(false);
				out.flush();
		        out.close();
		        return;
			}
		}
		catch(Exception ex) {
			
		}
	}
}
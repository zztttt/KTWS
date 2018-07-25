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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class manageCourse extends HttpServlet{
	
	private static final long serialVersionUID = 4659352391098773962L;
	
	@Autowired
	CourseDao coursedao;
	
	public manageCourse() {
		super();
	}
	@RequestMapping(value="/managecourse",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPOST");
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            
			String coursename = request.getParameter("name");
			String method = request.getParameter("method");
			System.out.println("coursename: " + coursename);
			System.out.println("method: "+method);

			if(method.equals("add")) {
				
			}
			
			Boolean isValid  = false;
            
            out.print(isValid);
            out.flush();
            out.close();
		}
		catch(Exception ex) {
			
		}
	}
}
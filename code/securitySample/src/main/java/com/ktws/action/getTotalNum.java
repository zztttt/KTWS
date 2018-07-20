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
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;
import com.ktws.Entity.User;

@RestController
public class getTotalNum extends HttpServlet{
	
	@Autowired
	PhotoDao photodao;
	@Autowired
	CourseDao coursedao;
	private static final long serialVersionUID = -951797589435947420L;
	public getTotalNum() {
		super();
	}
	@RequestMapping(value="/gettotalnum",method=RequestMethod.POST)
	protected void doPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPOST");
			String coursename = request.getParameter("name");
			System.out.println("coursename: " + coursename);
			int totalNum = coursedao.findToalByCoursename(coursename);
			System.out.println(totalNum);
			JSONArray jsonArray = new JSONArray();
			JSONObject o = new JSONObject();
			o.put("total",totalNum);
			jsonArray.add(o);
			Boolean isValid  = false;
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            out.print(totalNum);
            out.flush();
            out.close();
            
		}
		catch(Exception ex) {
			
		}
	}
}
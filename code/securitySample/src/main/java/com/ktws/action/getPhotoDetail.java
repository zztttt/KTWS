package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

import com.ktws.Dao.CourseDao;
import com.ktws.Dao.PhotoDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class getPhotoDetail extends HttpServlet{
	private static final long serialVersionUID = 5201346482949303774L;
	@Autowired
	CourseDao coursedao;
	@Autowired
	PhotoDao photodao;
	@RequestMapping(value="/getphotos",method=RequestMethod.POST)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doGet");
			String coursename = request.getParameter("name");
			System.out.println("classname: " + coursename);
			String tmpcoursename = "zztzzzclass0";
			List<Course> c = coursedao.findIdByCoursename(tmpcoursename);
			//int c = coursedao.findCountByCoursename(tmpcoursename);
			System.out.println(c);
			JSONArray jsonArray = new JSONArray();
			for(Course t:c) {
				Set<Photo> photos = t.getPhotoSet();
				for(Photo p:photos) {
					
					int id = p.getId();
					String url = p.getUrl();
					Date time = p.getDate();
					int total  = p.getTotal();
					JSONObject o = new JSONObject();
					o.put("id", id);
					o.put("filename", url);
					o.put("time", time.toString());
					o.put("num", total);
					System.out.println(o);
					jsonArray.add(o);
				}
			}
			Boolean isValid  = false;
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            out.print(jsonArray.toString());
            out.flush();
            out.close();
            
		}
		catch(Exception ex) {
			
		}
	}
}

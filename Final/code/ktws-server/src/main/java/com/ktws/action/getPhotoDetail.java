package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class getPhotoDetail extends HttpServlet{
	private static final long serialVersionUID = 5201346482949303774L;
	@PersistenceContext
	protected EntityManager em; 
	@Autowired
	CourseDao coursedao;
	@Autowired
	PhotoDao photodao;
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getphotos",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("getphotos func:doGet");
			String coursename = request.getParameter("name");
			System.out.println("classname: " + coursename);
			//String tmpcoursename = "zztzzzclass0";
			List<Course> c = coursedao.findByCoursename(coursename);
			//int c = coursedao.findCountByCoursename(tmpcoursename);
			//System.out.println(c);
			JSONArray jsonArray = new JSONArray();
			for(Course t:c) {
				//Set<Photo> photos = t.getPhotoSet();
				List<Photo> photos = em.createNativeQuery("select * from Photo p where course_id = ? order by photo_id desc", Photo.class)
						.setParameter(1, t.getId())
						.getResultList();
				
				for(Photo p:photos) {
					
					int id = p.getId();
					String url = p.getUrl();
					Date time = p.getDate();
					int total  = p.getTotal();
					int concentrated = p.getConcentration();
					JSONObject o = new JSONObject();
					o.put("id", id);
					o.put("filename", url);
					o.put("time", time.toString());
					o.put("num", total);
					o.put("focus", concentrated);
					//System.out.println(o);
					jsonArray.add(o);
				}
			}
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

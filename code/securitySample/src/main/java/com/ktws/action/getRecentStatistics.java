package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Set;

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
import com.ktws.Dao.UserDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class getRecentStatistics extends HttpServlet{

	private static final long serialVersionUID = -9051747642734608320L;

	@PersistenceContext
	protected EntityManager em; 
	@Autowired
	CourseDao coursedao;
	@Autowired
	PhotoDao photodao;
	
	public getRecentStatistics() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getRecentStatistic",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int num = 30;
		
		System.out.println("getRecentStatistic func:doGet");
		String coursename = request.getParameter("name");
		if (coursename == null) {
			System.out.println("getRecentStatistic: Parameter missing");
			return ;
		}
		
		List<Course> lc = coursedao.findByCoursename(coursename);
		if (lc.size() == 0) {
			System.out.println("getRecentStatistic: course not exist");
			return ;
		}
		Course c = lc.get(0);
		
		List<Photo> photos = em.createNativeQuery("select * from Photo p where course_id = ? order by photo_id desc limit ?", Photo.class)
				.setParameter(1, c.getId())
				.setParameter(2, num)
				.getResultList();
		if (photos.size() != num) {
			System.out.println("getRecentStatistic: photo number not right, photos.size:"+photos.size());
			return ;
		}
		JSONArray jsonArray = new JSONArray();
		double tmpTotal = 0;
		double tmpC = 0;
		for (int i=0; i<num; i++) {
			Photo p = photos.get(i);
			//System.out.println(p.getTotal());
			tmpTotal += p.getTotal();
			tmpC += p.getConcentration();
			if ((i+1) % 10 == 0) {
				//System.out.println("hh" + tmpTotal/10.0);
				jsonArray.add(tmpTotal/10.0);
				jsonArray.add(tmpC/10.0);
				tmpTotal = 0;
				tmpC = 0;
			}
		}
		
		System.out.println("statictic over:"+jsonArray.toString());
		
		PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.print(jsonArray.toString());
        out.flush();
        out.close();
	}
}

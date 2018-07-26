package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.ktws.Dao.UserDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;
import com.ktws.Entity.User;

import net.sf.json.JSONArray;

@RestController
public class getStatisticsByDay extends HttpServlet{

	private static final long serialVersionUID = 5207108741779336189L;

	@PersistenceContext
	protected EntityManager em; 
	@Autowired
	CourseDao coursedao;
	@Autowired
	PhotoDao photodao;
	@Autowired
	protected UserDao userdao;
	
	public getStatisticsByDay() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getStatisticsByDay",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		System.out.println("getStatisticsByDay func:doPost");
		String date = request.getParameter("date");
		if (date == null) {
			System.out.println("getStatisticsByDay: Parameter missing");
			return ;
		}
		System.out.println(date);
		date = date.replace("/", "");
		System.out.println(date);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		System.out.println("username: "+ username);
		
		User u = userdao.findByName(username);
		if (u == null ) {
			System.out.println("getStatisticsByDay: wrong");
		}
		
		Set<Course> sc = u.getCourseSet();
		List<Course> lc = new ArrayList<>(sc);
		Collections.sort(lc);
		List<Photo> photos = null;
		for (Course c :lc) {
			if (photos == null) {
				photos = em.createNativeQuery("select * from Photo p where p.course_id = ? and p.date=?", Photo.class)
						.setParameter(1, c.getId())
						.setParameter(2, date)
						.getResultList();
			}
			else {
				List<Photo> tmp = em.createNativeQuery("select * from Photo p where p.course_id = ? and p.date=?", Photo.class)
						.setParameter(1, c.getId())
						.setParameter(2, date)
						.getResultList();
				photos.addAll(tmp);
			}
		}
		System.out.println(photos.size());
		
		// divide into 6 group
		JSONArray jsonArray;
		if (photos.size() < 6) {
			System.out.println("getRecentStatistic: photo number not enough, <6");
			Object[] tmp = new Object[] {0,0,0,0,0,0,0,0,0,0,0,0,0};
			jsonArray = JSONArray.fromObject(tmp);
		}
		else {
			int num = photos.size() - photos.size() % 6; 
			
			jsonArray = new JSONArray();
			jsonArray.add(num);
			double tmpTotal = 0;
			double tmpC = 0;
			for (int i=0; i<num; i++) {
				Photo p = photos.get(i);
				tmpTotal += p.getTotal();
				tmpC += p.getConcentration();
				
				if ((i+1) % (num/6) == 0) {
					jsonArray.add(tmpTotal/(num/6));
					jsonArray.add(tmpC/(num/6));
					tmpTotal = 0;
					tmpC = 0;
				}
			}
		}
		
		PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.print(jsonArray.toString());
        out.flush();
        out.close();
		
	}
}

package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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

import net.sf.json.JSONArray;

@RestController
public class getAtmosphere extends HttpServlet{

	private static final long serialVersionUID = 8879318987537280329L;

	@PersistenceContext
	protected EntityManager em; 

	@Autowired
	PhotoDao photodao;
	@Autowired
	CourseDao coursedao;
	
	@RequestMapping(value="/getAtmosphere",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		System.out.println("getAtmosphere func:doPost");
		
		String coursename = request.getParameter("coursename");
		if (coursename == null) {
			System.out.println("getAtmosphere: Parameter missing");
			return ;
		}
		
		List<Course> lc = coursedao.findByCoursename(coursename);
		if (lc.size() == 0) {
			System.out.println("getAtmosphere: course not exist");
			return ;
		}
		Course c = lc.get(0);
		
		double attendance = photodao.getAttendance(c.getTotal(), c.getId());
		double concentration = photodao.getConcentration(c.getId());
		System.out.println("attendance:"+attendance +" concentration:"+concentration);
		
		DecimalFormat df = new DecimalFormat("#.0000");
		String atte = df.format(attendance);
		String conc = df.format(concentration);	
		attendance = Double.parseDouble(atte);
		concentration = Double.parseDouble(conc);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(attendance);
		jsonArray.add(concentration);
		System.out.println(jsonArray.toString());
		/*
		String atmosphere = "";
		String suggestion = "";
		
		if (attendance>0.8 && concentration>0.8) {
			atmosphere = "非常积极";
			suggestion = "上课非常好，同学们都爱听您的课，请您继续努力！";
		}
		else if (attendance<0.6 && concentration<0.6) {
			atmosphere = "略显消极";
			suggestion = "最近的课效果不是很理想，同学们兴趣不很高，请您适当调整上课的方式与节奏，提高同学们的兴趣！";
		}
		else {
			atmosphere = "积极";
			suggestion = "上课效果较好，同学们大都认真听讲，请您适当关注课堂氛围，调整教学安排！";
		}
		
		DecimalFormat df = new DecimalFormat("0.00%");
		String atte = df.format(attendance);
		String conc = df.format(concentration);		
		
		String res = String.format("尊敬的老师，你%s课的平均出勤率为%s，平均专注听课率为%s，课堂氛围%s，老师您%s", coursename, atte, conc, atmosphere, suggestion);
		System.out.println(res);
		*/
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		out.write(jsonArray.toString());
        out.flush();
        out.close();
		
	}
}

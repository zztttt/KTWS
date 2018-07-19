package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import com.ktws.Dao.PhotoDao;
import com.ktws.Entity.Photo;
import com.ktws.Entity.User;

@RestController
public class getLoginUsername extends HttpServlet{

	private static final long serialVersionUID = -951797589435947420L;
	public getLoginUsername() {
		super();
	}
	
	@Autowired
	protected PhotoDao pd;
	
	@RequestMapping(value="/getusername",method=RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doGet");
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			System.out.println("username: "+ username);
			JSONObject o = new JSONObject();
			o.put("username",username);
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            out.print(o);
            out.flush();
            out.close();
            
            List<Photo> l = pd.findAll();
            for (Photo p: l) {
                System.out.printf("%s, %s\n",p.getDate(),new SimpleDateFormat("EE",Locale.US).format(p.getDate()));
            }
		}
		catch(Exception ex) {
			
		}
	}
}

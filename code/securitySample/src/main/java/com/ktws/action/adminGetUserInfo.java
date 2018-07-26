package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.UserDao;
import com.ktws.Entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class adminGetUserInfo extends HttpServlet{
	
	@Autowired
	UserDao userdao;
	private static final long serialVersionUID = -1072373732511243902L;
	public adminGetUserInfo() {
		super();
	}
	@RequestMapping(value="/admingetuserinfo",method=RequestMethod.GET)
	protected void doGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doGET");
			PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            
			List<User> users = userdao.findAll();
			
			
			JSONArray jsonArray = new JSONArray();
			int i = 1;
			for( User user:users ) {
				//System.out.println(user);
				if(user.getRole() != null) {
					if(user.getRole().equals("ROLE_ADMIN"))
						continue;
				}
				JSONObject o = new JSONObject();
				o.put("key", i);
				o.put("user_id", user.getId());
				o.put("username", user.getUsername());
				o.put("password", user.getPassword());
				jsonArray.add(o);
				i++;
			}
			System.out.println("jsonarray:" + jsonArray.toString());
			out.println(jsonArray.toString());	
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			
		}
	}
}
package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Entity.User;
import com.ktws.Dao.UserDao;

@RestController
public class signUp extends HttpServlet{
	private static final long serialVersionUID = -953362352632712418L;
	
	@Autowired
	protected UserDao userdao;
	
	public signUp() {
		super();
	}
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("func:doPost");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("username: "+ username);
			System.out.println("password: "+ password);
			Boolean isValid = false;
			if(username.length()!=0 &&password.length()!=0)
				isValid = signup(username, password);
			else
				isValid = false;
			//JSONObject o = new JSONObject();
			//o.put("username",username);
			String ans = isValid? "success":"failed";
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            out.print("<script>alert("+ "'"+ans+"'" + ");"
            		//+ "history.go(-1);"
            		+ "var str = window.location.href;"
            		+"var num = str.indexOf(\"0\");"
            		+"str = str.substr(0, num-1);"
            		+"var h = str+\"8080/login.html\";"
            		+"window.location = h;"
            		+ "</script>");
            //out.print(ans);
            out.flush();
            out.close();
		}
		catch(Exception ex) {
			
		}
	}
	private Boolean signup(String username, String password) {
		Boolean isValid = false;
		int count = userdao.findCount(username);
		if(count != 0) {
			System.out.println("count != 0");
			return isValid;
		}
		else {
			int min = 1;
			int max = 10000;
			int id = min + ((int) (new Random().nextFloat() * (max - min)));
			while(id > 0) {
				int tmp = userdao.findCountId(id);
				if(tmp == 0)
					break;
				else id = min + ((int) (new Random().nextFloat() * (max - min)));
			}
			User res = new User();
			res.setId(id);
			res.setUsername(username);
			res.setPassword(password);
			res.setRole("ROLE_USER");
			System.out.println(res);
			userdao.save(res);
			isValid = true;
			return isValid;
		}
	}
}

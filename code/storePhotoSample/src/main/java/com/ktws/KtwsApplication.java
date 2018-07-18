package com.ktws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Entity.User;
import com.ktws.Grab.Grab;
import com.ktws.Dao.UserDao;

@SpringBootApplication
@RestController
public class KtwsApplication {
	@Autowired
	UserDao userdao;
	
	public static void main(String[] args) {
		
		SpringApplication.run(KtwsApplication.class, args);
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Grab g = new Grab();					
					g.grab(0,"zztzzzclass0");  // here is class name, but not good 
				} catch (java.lang.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	
	@RequestMapping("/getuser")  
    public List<User> getUser(){  
        List<User> users = userdao.findAll();
        return users;
    }
	@RequestMapping("/admin")  
    public String admin(){  
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
			 
		String name = userDetails.getUsername();
        return "this is admin: " + name;
    }
}

package com.ktws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Entity.User;
import com.ktws.Dao.UserDao;

@SpringBootApplication
@RestController
public class KtwsApplication {
	@Autowired
	UserDao userdao;
	
	public static void main(String[] args) {
		SpringApplication.run(KtwsApplication.class, args);
	}
	@RequestMapping("/getuser")  
    public List<User> getUser(){  
        List<User> users = userdao.findAll();
        return users;
    }
}

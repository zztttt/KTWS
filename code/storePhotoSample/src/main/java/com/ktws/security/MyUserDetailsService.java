package com.ktws.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ktws.Dao.UserDao;

import com.ktws.Entity.AuthUser;
import com.ktws.Entity.User;
//import org.springframework.security.core.userdetails.User;

@Component
public class MyUserDetailsService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserDao userdao;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户的用户名: {}", username);
        // TODO 根据用户名，查找到对应的密码，与权限

        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        //User user = new User(username, "123456",AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //return user;
        User user = userdao.findByName(username);
        if( user == null ){
        	logger.info("username not found");
        	throw new UsernameNotFoundException("User not found for name:"+username);
        }
        return new AuthUser(user);
    }
    /*public String getAuthorityByLoginId(String loginId ){
    	//Map<String,String> authKindMap = new HashMap<String,String>();
    	String auth = userdao.selectAuthorityByLoginId(loginId); 
    	return auth;
    	}*/
}

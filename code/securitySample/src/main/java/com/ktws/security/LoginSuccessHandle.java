package com.ktws.security;

import java.io.IOException;

import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class LoginSuccessHandle implements AuthenticationSuccessHandler{
	
	@Override  
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException,ServletException {
        
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = request.getContextPath() ;
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
        System.out.println(basePath);
        if (roles.contains("ROLE_ADMIN")){
        	
            response.sendRedirect(basePath+"admin/index.html");   
            return;
        }
        response.sendRedirect(basePath+"user/index.html");        
    }
}

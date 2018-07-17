package com.ktws.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	MyUserDetailsService userDetailsService;
	
	/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated();
    }*/
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/login.html").permitAll()
        	.antMatchers("/signup.html").permitAll()
        	.antMatchers("/signup").permitAll()
        	.antMatchers("/user/**").hasRole("USER")
        	.antMatchers("/admin").hasRole("ADMIN")
        	.anyRequest().authenticated()
        	.and()
        	.formLogin()   
        	.loginPage("/login.html") //  定义当需要用户登录时候，转到的登录页面。
        	.defaultSuccessUrl("/getuser")
        	.loginProcessingUrl("/user/login")
        	.and()
            .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
            .anyRequest()               // 任何请求,登录后可以访问
            .authenticated()
            .and()
            .csrf().disable(); 
    }
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
    	http//.authorizeRequests()
	        // 所有用户均可访问的资源
	        //.antMatchers("/login.html").permitAll()
	        // ROLE_USER的权限才能访问的资源
	        //.antMatchers("/user/**").hasRole("USER")
	        // 任何尚未匹配的URL只需要验证用户即可访问
	        //.anyRequest().authenticated()
	        //.and()
    		.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
            .loginPage("/login.html")           // 设置登录页面
            .loginProcessingUrl("/user/login")  // 自定义的登录接口
            .and()
            .authorizeRequests()        
            .antMatchers("/login.html")// 定义哪些URL需要被保护、哪些不需要被保护
            .permitAll()  // 设置所有人都可以访问登录页面
            .anyRequest()               // 任何请求,登录后可以访问
            .authenticated()
            .and()
            .csrf().disable();          // 关闭csrf防护
	}*/
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	  return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Configure spring security's authenticationManager with custom
        // user details service
        auth.userDetailsService(this.userDetailsService);
    }
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login.html?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
}

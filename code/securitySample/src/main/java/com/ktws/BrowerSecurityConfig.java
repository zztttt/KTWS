package com.ktws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{
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
        // 所有用户均可访问的资源
        .antMatchers("/static/**","/**").permitAll()
        // ROLE_USER的权限才能访问的资源
        .antMatchers("/user/**").hasRole("USER")
        // 任何尚未匹配的URL只需要验证用户即可访问
        .anyRequest().authenticated()
        .and()
	    		.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
	            .loginPage("/index.html")           // 设置登录页面
	            .loginProcessingUrl("/user/login")  // 自定义的登录接口
	            .and()
	            .authorizeRequests()        
	            .antMatchers("/index.html")// 定义哪些URL需要被保护、哪些不需要被保护
	            .permitAll()  // 设置所有人都可以访问登录页面
	            .anyRequest()               // 任何请求,登录后可以访问
	            .authenticated()
	            .and()
	            .csrf().disable();          // 关闭csrf防护
	}
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	  return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}

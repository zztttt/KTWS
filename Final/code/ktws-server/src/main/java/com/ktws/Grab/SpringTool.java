package com.ktws.Grab;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class SpringTool implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringTool.applicationContext == null) {
			SpringTool.applicationContext = applicationContext;
			System.out.println(
					"========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
							+ applicationContext + "========");
		}
	}
 
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
 
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> c) {
		return getApplicationContext().getBean(c);
	}
	
	public static <T> T getBean(String name, Class<T> c) {
		return getApplicationContext().getBean(name, c);
	}
}

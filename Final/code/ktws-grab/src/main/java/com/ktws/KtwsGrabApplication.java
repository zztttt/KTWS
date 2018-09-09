package com.ktws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ktws.Grab.Grab;

@SpringBootApplication
public class KtwsGrabApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(KtwsGrabApplication.class, args);
		
		/*
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("00000");
					Grab g = new Grab();					
					g.grab(0,"zztzzzclass0");  // here is class name, but not good 
				} catch (java.lang.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		Thread.sleep(2000);
		*/
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("11111");
					Grab g = new Grab();					
					g.grab(1,"zztzzzclass1");  // here is class name, but not good 
				} catch (java.lang.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		/*
		 * need two stop, or org.springframework.beans.factory.BeanCreationException
		 */
	}
}

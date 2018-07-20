package com.ktws.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktws.Dao.CourseDao;
import com.ktws.Dao.PhotoDao;
import com.ktws.Entity.Photo;

@RestController
public class getImage extends HttpServlet{

	private static final long serialVersionUID = -4993896687619190384L;

	@PersistenceContext
	protected EntityManager em; 

	@Autowired
	PhotoDao photodao;
	
	public getImage() {
		super();
	}
	
	@RequestMapping(value="/getImage",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		System.out.println("getImage func:doPost");
		int id = Integer.parseInt(request.getParameter("id"));
		if (request.getParameter("id") == null) {
			System.out.println("getImage: Parameter missing");
			return ;
		}
		
		Photo p = photodao.findById(id);
		if (p == null) {
			System.out.println("photo id mot exists");
			System.exit(0);
		}
		
		String filepath = p.getUrl();
		System.out.println(filepath);
		
		String marked = filepath.substring(0, filepath.length()-18);
		System.out.println(marked);
		marked = marked + "marked\\"+ filepath.substring(filepath.length()-18, filepath.length());
		System.out.println(marked);
		
		InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(marked);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        } 
        
        String res = Base64.encodeBase64String(data);
        
        //System.out.println(res);
        
        PrintWriter out = response.getWriter();
        //response.setContentType("text/html;charset=utf-8");
        out.print(res);
        out.flush();
        out.close();
	}
}

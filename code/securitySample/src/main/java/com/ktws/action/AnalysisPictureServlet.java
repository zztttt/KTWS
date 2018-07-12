package com.ktws.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.functions.AnalysisPicture;

@RestController
public class AnalysisPictureServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public AnalysisPictureServlet() {
		super();
	}
	@RequestMapping(value="/KTWS/AnalysisPicture",method=RequestMethod.POST)
	protected void DealPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			System.out.println("AnalysisPicture:dealpost");
			String PictureName = request.getParameter("picturename");

			String filepath = "D:\\学习\\暑期大作业\\image\\" + PictureName + ".jpg";
			System.out.println("filepath: "+filepath);
			AnalysisPicture apic = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP","bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");
			String datastr = apic.GetData(filepath);
            System.out.println(datastr);
            int NumOfFace = apic.NumOfFace(datastr);
            System.out.println(NumOfFace);
            List<String> FaceToken = apic.get_facetoken(datastr);
            System.out.println(FaceToken);
			
			/*MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase database = mongoClient.getDatabase("project");
			MongoCollection<DBObject> collection = database.getCollection("faces", DBObject.class);  
			DBObject bson = (DBObject)JSON.parse(res.toString());
			collection.insertOne(bson);*/
            
            
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");
            out.print("1");
            out.flush();
            out.close();
		}
		catch(Exception ex) {
			
		}
	}
	
}

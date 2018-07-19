package com.ktws.Photo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.bytedeco.javacv.FrameGrabber.Exception;

import com.functions.AnalysisPicture;
import com.ktws.Dao.CourseDao;
import com.ktws.Dao.PhotoDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;

import net.sf.json.JSONObject;

public class Grab {

	public Grab() {
		
	}
	
	@PersistenceContext
	static EntityManager em;
	
	@Autowired
	static PhotoDao photodao;
	
	@Autowired
	static CourseDao coursedao;
	
	protected static AnalysisPicture ap = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP", "bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");
	static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	public static void grab(Integer id, String coursename) throws java.lang.Exception{
		System.out.println("course:"+coursename);
		System.out.println(coursename.equals("zztzzzclass0"));
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(id);
        try{
        	grabber.start();// begin to get camera data
        }
        catch(Exception e){
        	grabber.close();
        	System.out.println("camera not exist");
            return;
        }

        String pathname = "d:\\img\\"+coursename + "\\";
        File dir = new File(pathname);
        judgeDirExists(dir);
        while(true)
        {
        	opencv_core.Mat mat = converter.convertToMat(grabber.grabFrame());
        	Date now=new Date();
        	System.out.println(now.toString());
        	// java.util.Date -> java.time.LocalDate
        	LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	// java.time.LocalDate -> java.sql.Date
        	java.sql.Date newDate=java.sql.Date.valueOf(localDate);
        	System.out.println(newDate.toString());
        	
        	// save photo 
        	String filepath = savePhoto(now, pathname, mat);
            
            // analysis photo
            String analysis = analysisAndMark(filepath);
            JSONObject json = JSONObject.fromObject(analysis);
            int total = json.getInt("total");
            int concentrate = json.getInt("concentrate");
            System.out.printf("%d, %d\n", total, concentrate);
            
            // store to database
            List<Course> l = coursedao.findIdByCoursename("zztzzzclass0");
            if (l.size() == 0) {
            	System.out.println("course name not exist");
            	System.exit(0);
            }
            Course c = l.get(0);
            
            Photo p = new Photo();
            p.setDate(newDate);
            p.setUrl(filepath);
            p.setTotal(total);
            p.setConcentration(concentrate);
            p.setCourse(c);
            em.persist(p);
            
            Thread.sleep(5000);//5秒刷新一次图像
        }
    }
	private static String savePhoto(Date date, String pathname, opencv_core.Mat mat) {
    	/*
    	 * pathname : img\\classname
    	 * dirname : Date like 20180718
    	 * filename : Date+Time like 20180718122050
    	 * path : img\\classname\\Date
    	 */
    	SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String dirname = df2.format(date);
        File dir2 = new File(pathname+dirname);
        judgeDirExists(dir2);
        
        String path = pathname+dirname;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = df.format(date);
        
        opencv_imgcodecs.imwrite(path + "\\" + filename + ".png", mat);
        
        return path + "\\" + filename + ".png";
	}
	
	private static String analysisAndMark(String filepath) throws java.lang.Exception {
		String detectStr = ap.detectByPath(filepath);
        String analysis = ap.analysisFaceAll(detectStr);
        System.out.println("this is analysis"+analysis);
        if (!ap.markPhoto(filepath, detectStr).equals("mark ok")) {
        	System.out.println("mark failed");
        	System.exit(0);
        }
        
        return analysis;
	}
	
	public static void judgeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }
    }
}

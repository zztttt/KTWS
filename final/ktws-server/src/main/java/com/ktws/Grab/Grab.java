package com.ktws.Grab;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.bytedeco.javacv.FrameGrabber.Exception;

import com.functions.AnalysisPicture;
import com.ktws.Dao.CourseDao;
import com.ktws.Dao.PhotoDao;
import com.ktws.Entity.Course;
import com.ktws.Entity.Photo;

import net.sf.json.JSONObject;

@Component
@Service("productService")
public class Grab {

	public Grab() {
		
	}
	/*
	 * this is a pramary class which is not scanned by spring
	 * so we cannot use @Autowired to get dao bean
	 * instead I create a springtool to get bean via ApplicationContext
	 * 
	 * we cannot use entity manager/Transaction to persist object which is beyond my knowledge
	 * instead use save from dao is ok
	 * 
	 * besides, if we don't close grabber in catch body when exception occur, then Eclipse boom, but why?
	 * 
	 */
	
	protected static AnalysisPicture ap = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP", "bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");
	static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

	@Transactional 	
	public void grab(Integer id, String coursename) throws java.lang.Exception{
		
		// here we get dao !!
		PhotoDao photodao = (PhotoDao) SpringTool.getBean(PhotoDao.class);
		CourseDao coursedao = (CourseDao) SpringTool.getBean(CourseDao.class);
		
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
        int i = 0;
        while(true)
        {
        	opencv_core.Mat mat = converter.convertToMat(grabber.grabFrame());
        	if (i==0) {
        		i++;
        		continue;
        	}
        	Date now=new Date();
        	//System.out.println(now.toString());
        	// java.util.Date -> java.time.LocalDate
        	LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        	// java.time.LocalDate -> java.sql.Date
        	java.sql.Date newDate=java.sql.Date.valueOf(localDate);
        	//System.out.println(newDate.toString());
        	
        	// save photo 
        	String filepath = savePhoto(now, pathname, mat);
            
            
        	try {
        		// analysis photo
        		String analysis = analysisAndMark(filepath);
        		if (analysis.equals("fail")) {
        			System.out.println("no face found");
        			continue;
        		}
        		
                JSONObject json = JSONObject.fromObject(analysis);
                int total = json.getInt("total");
                int concentrate = json.getInt("concentrate");
                //System.out.printf("%d, %d\n", total, concentrate);
                
                // store to database
                List<Course> l = coursedao.findByCoursename(coursename);
                
                if (l.size() == 0) {
                	System.out.println("course name not exist");
                	System.exit(0);
                }
                Course c = l.get(0);
                //Course c = coursedao.findById((long)3).get();
                //photodao.findAll();
                Photo p = new Photo();
                p.setDate(newDate);
                p.setUrl(filepath);
                p.setTotal(total);
                p.setConcentration(concentrate);
                
                c.addPhoto(p);
                
                // this works without Transaction or there will be much exception(below)
                coursedao.save(c);
                
        	}catch (Exception e) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (net.sf.json.JSONException e) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (java.lang.NullPointerException e) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (org.hibernate.LazyInitializationException e ) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (javax.persistence.TransactionRequiredException e) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (java.lang.IllegalStateException e) {
        		e.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}catch (Throwable th) {
        		System.out.println("in throwable");
        		//th.printStackTrace();
        		grabber.stop();// stop grab
                grabber.close();
                System.exit(2);
        	}
            
            // ！！！！！！！！！！！！！！this should be changed
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
        //System.out.println("this is analysis"+analysis);
        if (!ap.markPhoto(filepath, detectStr).equals("mark ok")) {
        	System.out.println("mark failed");
        	return "fail";
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

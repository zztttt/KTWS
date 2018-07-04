package grabimage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Grab{

	public Grab() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws Exception, InterruptedException{
		Thread thread = new Thread(new Concur());
		thread.start();
	}
	static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	public static void grab(Integer num, String classname) throws Exception, InterruptedException{
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(num);
        try{
        	grabber.start();//��ʼ��ȡ����ͷ����
        }
        catch(Exception e){
        	grabber.close();
        	System.out.println("����ͷ������");
            return;
        }
        CanvasFrame canvas = new CanvasFrame("����ͷ");//�½�һ������
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        String dirname = "d:\\img\\"+classname + "\\";
        File dir = new File(dirname);
        Grab.judeDirExists(dir);
        while(true)
        {
            if(!canvas.isDisplayable())
            {//�����Ƿ�ر�
                grabber.stop();//ֹͣץȡ
                grabber.close();
                System.exit(2);//�˳�
                break;
            }       
            canvas.showImage(grabber.grab());//��ȡ����ͷͼ�񲢷ŵ���������ʾ�� �����Frame frame=grabber.grab(); frame��һ֡��Ƶͼ��
            opencv_core.Mat mat = converter.convertToMat(grabber.grabFrame());
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date=new Date();
            String dateString = df.format(date);
            
            SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
            String dateString2 = df2.format(date);
            File dir2 = new File(dirname+dateString2);
            Grab.judeDirExists(dir2);
            
            opencv_imgcodecs.imwrite(dirname + dateString2 + "\\" + dateString + ".png", mat);
            Thread.sleep(5000);//5��ˢ��һ��ͼ��
        }
    }
	
	public static void judeDirExists(File file) {

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

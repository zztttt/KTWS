package grabimage;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class Concur implements Runnable{

	public Concur() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Grab.grab(0,"first");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

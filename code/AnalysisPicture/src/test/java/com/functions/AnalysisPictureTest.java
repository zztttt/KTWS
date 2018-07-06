package com.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AnalysisPictureTest extends TestCase {
	
	protected AnalysisPicture ap = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP", "bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");;
	protected String thisPath = Class.class.getClass().getResource("/").getPath();;
	//protected StringBuilder dataStrBuilder;
	
	/*
	@Before
	public void before() throws Exception {
		String dataStr = ap.GetData(thisPath+"2.png");
		dataStrBuilder.append(dataStr);
		System.out.println(dataStr);
	}*/
	/*
	@Test
	public void testGetData() throws Exception {
		String dataStr = ap.detectByPath(thisPath+"2.png");
		//System.out.println(dataStrBuilder.toString());
		assertFalse(dataStr.equals("picture not exists!"));
		assertFalse(dataStr.equals("getdata fail"));
	}

	@Test
	public void testNumOfFace() throws Exception {
		String dataStr = ap.detectByPath(thisPath+"2.png");
		System.out.println(dataStr);
		int num = ap.numOfFace(dataStr);
		System.out.println(num);
		assertEquals(num,30);
	}*/

	public void testAnalysisFace() throws Exception {
		String dataStr = ap.detectByPath("E:\\SummerProject\\photo\\4.jpg");
		System.out.println(dataStr);
		JSONObject dataJson = JSONObject.fromObject(dataStr);
        JSONArray faceset = dataJson.getJSONArray("faces");
        int count = faceset.size(); // number of faces
        
        String ftokens= "";
        for (int i=0; i<count; i++) {
        	JSONObject face = JSONObject.fromObject(faceset.get(i));
        	String oneToken = face.get("face_token").toString();
        	System.out.println(oneToken);
        	if (i != 0) ftokens += ",";
        	ftokens += oneToken;
        }
        // analysis face 
        System.out.println(ftokens);
        String res = ap.analysisFace(ftokens);
    	System.out.println(res);
    	assertFalse(res.equals("analysisface fail"));
    	
    	JSONObject resJson = JSONObject.fromObject(res);
    	JSONArray resArray = resJson.getJSONArray("faces");
    	int resCount = resArray.size();
    	
    	List<String> tmpList = new ArrayList<>();
    	//tmpList.add("python");
    	//tmpList.add("E:\\SummerProject\\pycode\\markPicture.py");
    	String tmp = "E:\\python3\\python.exe E:\\SummerProject\\pycode\\markPicture.py";
    	tmp += " E:\\SummerProject\\photo";
    	tmp += " 4.jpg";
    	//tmpList.add("E:\\SummerProject\\photo");
    	//tmpList.add("4.jpg");
    	for (int i=0;i<resCount;i++) {
    		JSONObject face = JSONObject.fromObject(faceset.get(i));
    		JSONObject rectangle = (JSONObject)face.get("face_rectangle");
    		System.out.println(rectangle.toString());
    		
    		int top = Integer.parseInt(rectangle.get("top").toString());
    		int left = Integer.parseInt(rectangle.get("left").toString());
    		int width = Integer.parseInt(rectangle.get("width").toString());
    		int height = Integer.parseInt(rectangle.get("height").toString());
    		System.out.printf("left: %d, top:%d, width: %d, height: %d\n", left, top, width, height);
    		
    		//tmpList.add(String.valueOf(left));
    		//tmpList.add(String.valueOf(top));
    		//tmpList.add(String.valueOf(width));
    		tmp += " "+left;
    		tmp += " "+top;
    		tmp += " "+width;
    	}
    	

    	//String[] args = tmpList.toArray(new String[tmpList.size()]);
    	System.out.println(tmp);
    	
    	try {
    		Process proc = Runtime.getRuntime().exec(tmp);
    		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    		String line = null;
    		while ((line = in.readLine()) != null) {
    			System.out.println(line);
    		}
    		in.close();
	        int code = proc.waitFor();
	        System.out.println("hhhh code: "+ code);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	/*
	public void testGetBytesFromFile() {
		fail("Not yet implemented");
	}
	*/


}

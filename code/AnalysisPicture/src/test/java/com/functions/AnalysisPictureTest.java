package com.functions;

import java.io.BufferedReader;
import java.io.File;
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
	
	protected AnalysisPicture ap = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP", "bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");
	protected String thisPath = AnalysisPictureTest.class.getClass().getResource("/").getPath(); // \E:\SummerProject\workspace\analysisPhoto\target\test-classes
	
	@Test
	public void testdDetectByPath() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"2.png");
		//System.out.println(detectStr);
		assertFalse(detectStr.equals("picture not exists!"));
		assertFalse(detectStr.equals("getdata fail"));
	}

	@Test
	public void testNumOfFace() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"2.png");
		//System.out.println(detectStr);
		int num = ap.numOfFace(detectStr);
		//System.out.println(num);
		assertEquals(num,2);
		
		detectStr = ap.detectByPath(thisPath+"4.jpg");
		num = ap.numOfFace(detectStr);
		assertEquals(num, 3);
	}

	/* code below is useless now
	public void testAnalysisFace() throws Exception {
		String detectStr = ap.detectByPath("E:\\SummerProject\\photo\\4.jpg");
		System.out.println(detectStr);
		JSONObject detectJson = JSONObject.fromObject(detectStr);
        JSONArray faceset = detectJson.getJSONArray("faces");
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
    	System.out.println("xxxx:" + res);
    	assertFalse(res.equals("analysisface fail"));
    	
    	JSONObject resJson = JSONObject.fromObject(res);
    	JSONArray resArray = resJson.getJSONArray("faces");
    	int resCount = resArray.size();
    	
    	List<String> tmpList = new ArrayList<>();
    	//tmpList.add("python");
    	//tmpList.add("E:\\SummerProject\\pycode\\markPicture.py");
    	String tmp = "python E:\\SummerProject\\pycode\\markPicture.py";
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
*/
	
	public void testAnalysisFaceByTokens() throws Exception {
		//System.out.println("in All\n");
		String detectStr = ap.detectByPath(thisPath+"4.jpg");
		//System.out.println(detectStr);
		JSONObject detectJson = JSONObject.fromObject(detectStr);
        JSONArray faceset = detectJson.getJSONArray("faces");
        int count = faceset.size(); // number of faces
        assertEquals(count, 3);
        
        String ftokens= "";
        for (int i=0; i<count; i++) {
        	JSONObject face = JSONObject.fromObject(faceset.get(i));
        	String oneToken = face.get("face_token").toString();
        	//System.out.println(oneToken);
        	if (i != 0) ftokens += ",";
        	ftokens += oneToken;
        }
        // analysis face 
        //System.out.println(ftokens);
        String analysisStr = ap.analysisFaceByTokens(ftokens);
		//System.out.println(analysisStr);
		assertFalse(analysisStr.equals("analysisArray"));
	}
	
	public void testAnalysisFaceAll() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"8.jpg");
		String res = ap.analysisFaceAll(detectStr);
		//System.out.println(res);
		JSONObject json = JSONObject.fromObject(res);
		int total = Integer.parseInt(json.get("total").toString());
		int concentrate = Integer.parseInt(json.get("concentrate").toString());
		//System.out.println("total:"+total+" concentrate: "+ concentrate);
	}
	
	public void testMarkPhoto() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"4.jpg");
		//System.out.println(thisPath+"4.jpg");
		String res = ap.markPhoto(thisPath+"4.jpg", detectStr);
		System.out.println(res);
		assertFalse(!res.equals("mark ok"));
		File file = new File(thisPath + "marked/4.jpg");
		assertFalse(!file.exists());
	}

}

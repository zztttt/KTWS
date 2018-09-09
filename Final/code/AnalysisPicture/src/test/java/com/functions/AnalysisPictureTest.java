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
	
	@Test
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
	
	@Test
	public void testAnalysisFaceAll() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"8.jpg");
		String res = ap.analysisFaceAll(detectStr);
		//System.out.println(res);
		JSONObject json = JSONObject.fromObject(res);
		int total = Integer.parseInt(json.get("total").toString());
		int concentrate = Integer.parseInt(json.get("concentrate").toString());
		//System.out.println("total:"+total+" concentrate: "+ concentrate);
	}
	
	@Test
	public void testMarkPhoto() throws Exception {
		String detectStr = ap.detectByPath(thisPath+"4.jpg");
		//System.out.println(thisPath+"4.jpg");
		String res = ap.markPhoto(thisPath+"4.jpg", detectStr);
		//System.out.println(res);
		assertFalse(!res.equals("mark ok"));
		File file = new File(thisPath + "marked/4.jpg");
		assertFalse(!file.exists());
	}

}

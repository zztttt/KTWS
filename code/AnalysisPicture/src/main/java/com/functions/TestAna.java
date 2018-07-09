package com.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestAna {

	public void main() throws Exception {
		AnalysisPicture ap = new AnalysisPicture("pjvZCfFaVo3rW3GwUahcbl1vGjc2O8DP", "bdepZ25FXgP9WXcURlfYpBPbxVFY3qEx");;
		String thisPath = Class.class.getClass().getResource("/").getPath();
		
		String dataStr = ap.detectByPath(thisPath+"4.jpg");
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
    	//assertFalse(res.equals("analysisface fail"));
    	
    	JSONObject resJson = JSONObject.fromObject(res);
    	JSONArray resArray = resJson.getJSONArray("faces");
    	int resCount = resArray.size();
    	
    	List<String> tmpList = new ArrayList<String>();
    	//tmpList.add("python");
    	//tmpList.add("E:\\SummerProject\\pycode\\markPicture.py");
    	tmpList.add(thisPath);
    	tmpList.add("4.jpg");
    	for (int i=0;i<resCount;i++) {
    		JSONObject face = JSONObject.fromObject(faceset.get(i));
    		JSONObject rectangle = (JSONObject)face.get("face_rectangle");
    		System.out.println(rectangle.toString());
    		
    		int top = Integer.parseInt(rectangle.get("top").toString());
    		int left = Integer.parseInt(rectangle.get("left").toString());
    		int width = Integer.parseInt(rectangle.get("width").toString());
    		int height = Integer.parseInt(rectangle.get("height").toString());
    		System.out.printf("left: %d, top:%d, width: %d, height: %d\n", left, top, width, height);
    		
    		tmpList.add(String.valueOf(left));
    		tmpList.add(String.valueOf(top));
    		tmpList.add(String.valueOf(width));
    	}
    	
    	String[] args = tmpList.toArray(new String[tmpList.size()]);
    	System.out.println(tmpList.toString());
    	
    	Process proc = Runtime.getRuntime().exec("E:\\python3\\python.exe E:\\SummerProject\\pycode\\markPicture.py"+ tmpList);
    	BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        proc.waitFor();
	}
}

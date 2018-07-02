package com.function;

import com.java.FaceTest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Functions {
	int NumofFace(String datastr) throws Exception {
		JSONObject json = JSONObject.fromObject(datastr);
        JSONArray faceset = (JSONArray) json.get("faces");
        int number = faceset.size();
		return number;
	}
	
}

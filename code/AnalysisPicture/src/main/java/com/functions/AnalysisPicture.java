package com.functions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AnalysisPicture {
	
	private final static int CONNECT_TIME_OUT = 30000;
	private final static int READ_OUT_TIME = 50000;
	private static String boundaryString = getBoundary();
	private static HashMap<String, String> map = new HashMap<String, String>();
    private static HashMap<String, byte[]> byteMap = new HashMap<String, byte[]>();
    
    private static String api_key, api_secret;
    
	public AnalysisPicture(String key, String secret){
		api_key = key;
		api_secret = secret;
    }
	
	private void MapClear() {
		map.clear();
		map.put("api_key", api_key);
        map.put("api_secret", api_secret);
	}
	
   /* Detect the photo and get the face data from the picture*/
	public String detectByPath(String filepath) throws Exception{
		MapClear();
		//System.out.println("in detect, path "+filepath);
   		String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
        try{
        	File file = new File(filepath);
        	if(!file.exists())    
        	{
        	    return "picture not exists!";
        	}
          	byte[] buff = getBytesFromFile(file);
        	map.put("return_landmark", "0"); 
            map.put("return_attributes", "emotion");
            byteMap.put("image_file", buff);
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            
            // deal with api error "CONCURRENCY_LIMIT_EXCEEDED", detect again
            while (str.equals("{\"error_message\":\"CONCURRENCY_LIMIT_EXCEEDED\"}")) {
            	// console print to debug
            	System.out.println("post again");
            	bacd = post(url, map, byteMap);
                str = new String(bacd);
            }
            return str;
        }catch (Exception e) {
       	 	return "getdata fail";
   		}
   }
    
    /*Count the number of face from the String get from the picture*/
	public int numOfFace(String detectStr) throws Exception {
		JSONObject json = JSONObject.fromObject(detectStr);
        JSONArray faceset = json.getJSONArray("faces");
        int number = faceset.size();
		return number;
	}	
	
	/*Analysis a face according to a face_token*/
	// should not be a face, should analysis all face 
	public String analysisFaceByTokens(String facetoken) throws Exception {
		String url = "https://api-cn.faceplusplus.com/facepp/v3/face/analyze";
        map.put("face_tokens", facetoken);   
        map.put("return_attributes", "eyestatus,emotion");
        try {
    		byte[] bacd = post(url, map, byteMap);
    		String str = new String(bacd);
    		return str;
    	}catch (Exception e) {
        	return "analysisface fail";
    	}
	}
	
	/*Analysis all face according to detect string */
	public String analysisFaceAll(String detectStr) throws Exception {
		JSONObject detectJson = JSONObject.fromObject(detectStr);
		JSONArray faceset = detectJson.getJSONArray("faces");
		int count = faceset.size();
		if (count==0)
			return "no face found in analysis all";
		
		int concentrate = 0;
		
		// face number <= 5
		if (count <= 5) {
			for (int i=0; i<count; i++) {
				JSONObject face = JSONObject.fromObject(faceset.get(i));
				JSONObject attributes = (JSONObject)face.get("attributes");
				String oneEmotion = attributes.get("emotion").toString();
				if (emotionDeal(oneEmotion)) {
					concentrate += 1;
				}
			}
		}
		else { // face number > 5
			// list to store face token
			List<String> tokenList = new ArrayList<String>();
			for (int i=0; i<count; i++) {
				JSONObject face = JSONObject.fromObject(faceset.get(i));
				String oneToken = face.get("face_token").toString();
				//System.out.println(oneToken);
				tokenList.add(oneToken);
			}

			String tokens = "";
			for (int i=0; i<count; i++) {
				if (i % 5 != 0) tokens += ",";
				tokens += tokenList.get(i);
				if (((i+1) % 5 == 0) || ((i+1) == count)) {
					// deal with analysis return value
					String analysisStr = analysisFaceByTokens(tokens);
					JSONObject analysisJson = JSONObject.fromObject(analysisStr);
					JSONArray analysisArray = analysisJson.getJSONArray("faces");
					int analysisCount = analysisArray.size();
					for (int j=0; j<analysisCount; j++) {
						JSONObject face = JSONObject.fromObject(analysisArray.get(j));
						JSONObject attributes = (JSONObject)face.get("attributes");
						String oneEmotion = attributes.get("emotion").toString();
						if (emotionDeal(oneEmotion)) {// judge emotion
							concentrate += 1;
						}
					}
					// refresh tokens
					tokens = "";
				}
			}
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("total", count);
		map.put("concentrate", concentrate);
		JSONObject res = JSONObject.fromObject(map);
		return res.toString();
	}
	
	private boolean emotionDeal(String emotion) {
		JSONObject emotionJson = JSONObject.fromObject(emotion);
		double sadness = Double.parseDouble(emotionJson.getString("sadness"));
		double neutral = Double.parseDouble(emotionJson.getString("neutral"));
		double disgust = Double.parseDouble(emotionJson.getString("disgust"));
		double anger = Double.parseDouble(emotionJson.getString("anger"));
		double surprise = Double.parseDouble(emotionJson.getString("surprise"));
		double fear = Double.parseDouble(emotionJson.getString("fear"));
		double happiness = Double.parseDouble(emotionJson.getString("happiness"));
		
		double flag = 2*happiness + 2*surprise + neutral - 0.6*sadness - 0.6*fear - 0.4*disgust - 0.4*anger;
		if (flag > 0.7)
			return true;
		else 
			return false;
	}
	
	/* mark picture with face rectangle using python pillow */ 
	public String markPhoto(String filepath, String detectStr) {
		/* .class.getClass().getResource("/").getPath() 得到的路径最前面有一个斜杠(??why)，会出现问题，去除之 */
		if (filepath.charAt(0) == '\\' || filepath.charAt(0) == '/') {
			//System.out.println("mark in remove");
			filepath = filepath.substring(1, filepath.length());
			//System.out.println("NEW : "+filepath);
		}
		JSONObject detectJson = JSONObject.fromObject(detectStr);
		JSONArray faceset = detectJson.getJSONArray("faces");
		int count = faceset.size();
		if (count==0)
			return "no face found in mark photo";
		
		// arguments for python cmd line
		String args = "python E:\\SummerProject\\pycode\\markPicture.py "; // py code path
		args += filepath; // file path with file name
		for (int i=0; i<count; i++) {
			JSONObject face = JSONObject.fromObject(faceset.get(i));
			JSONObject rectangle = (JSONObject)face.get("face_rectangle");
        	int top = Integer.parseInt(rectangle.get("top").toString());
    		int left = Integer.parseInt(rectangle.get("left").toString());
    		int width = Integer.parseInt(rectangle.get("width").toString());
    		//int height = Integer.parseInt(rectangle.get("height").toString());
        	args += " "+left+" "+top+" "+width;
		}

		//System.out.println(args);
    	
    	try {
    		Process proc = Runtime.getRuntime().exec(args);
    		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    		String line = null;
    		while ((line = in.readLine()) != null) {
    			System.out.println(line);
    		}
    		in.close();
	        int code = proc.waitFor();
	        
	        //System.out.println("hhhh code: "+ code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return "mark fail";
    	}
		
		return "mark ok";
	}
	/*
	 * this is original
	 * */
	public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
	
	private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
	
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
    
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
}
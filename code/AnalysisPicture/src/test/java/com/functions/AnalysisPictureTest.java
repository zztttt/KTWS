package com.functions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

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
	
	@Test
	public void testGetData() throws Exception {
		String dataStr = ap.GetData(thisPath+"2.png");
		//System.out.println(dataStrBuilder.toString());
		assertFalse(dataStr.equals("picture not exists!"));
		assertFalse(dataStr.equals("getdata fail"));
	}

	public void testNumOfFace() throws Exception {
		String dataStr = ap.GetData(thisPath+"2.png");
		System.out.println(dataStr);
		int num = ap.NumOfFace(dataStr);
		assertEquals(num,2);
	}

	public void testGet_facetoken() {
		fail("Not yet implemented");
	}

	public void testCreate_faceset() {
		fail("Not yet implemented");
	}

	public void testDelete_faceset() {
		fail("Not yet implemented");
	}

	public void testAddface() {
		fail("Not yet implemented");
	}

	public void testAnalysisface() {
		fail("Not yet implemented");
	}

	public void testGetBytesFromFile() {
		fail("Not yet implemented");
	}


}

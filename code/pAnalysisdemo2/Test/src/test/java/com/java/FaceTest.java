package com.java;

import java.util.HashSet;
import com.function.Functions;

public class FaceTest {
public static void main(String[] args) throws Exception{
        String str = Functions.getData("C:\\Users\\lenovo\\Desktop\\timg.jpg");
        //List<String> list = Functions.get_facetoken(str);
        String faceset = Functions.create_faceset("Fjjismyson");
        System.out.println(faceset);
        String v = Functions.addface(faceset, str);
        String delete = Functions.delete_faceset(faceset);
        System.out.println(delete);
        //int numofpeople = Functions.NumofFace(str);
        //System.out.println(numofpeople);
	}
}

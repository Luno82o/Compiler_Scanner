import java.io.*;
import java.util.*;

public class Main {	
    
	public static void main(String[] args) {
	    Scanner sc = new Scanner();
	    
        //輸入檔名
        System.out.print("filename(xxx.txt):");

//        java.util.Scanner input = new java.util.Scanner(System.in);
//        String filename = input.nextLine();
//        input.close();
        String filename = "test.txt";
        
	    sc.readTxt(filename);
	    sc.scan();

//	    main2("aaa", "bbb");
//	    main2("ccc", "ddd");
	}
	
//	public static Token identifier = new Token();
//	public static Token character = new Token();
//	public static void main2(String a, String b) {
//
//	    identifier.addMap(a);
//	    character.addMap(b);
//	    identifier.pMap();
//	    character.pMap();
//	}
}

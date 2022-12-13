import java.io.*;
import java.util.*;

public class Main {	
    
	public static void main(String[] args) {
	    Scanner sc = new Scanner();
	    Token tokens = new Token();
//	    tokens.setTokenClass();
	    
        //輸入檔名
        System.out.print("filename(xxx.txt):");

//        java.util.Scanner input = new java.util.Scanner(System.in);
//        String filename = input.nextLine();
//        input.close();
        String filename = "test.txt";
        
	    sc.readTxt(filename);
	    sc.scan();
	    //test
//	    tokens.coutTokenBuf();
//	    String tmp = tokens.getTokenBuf(1, 2);
//        System.out.print(tmp);
//	    tokens.addRWMap("int");
//	    tokens.addRWMap("int");
//	    tokens.addRWMap("cout");
	    tokens.pRWMap();
	}
	
}

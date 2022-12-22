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
        String filename = "test2.txt";
        
	    sc.readTxt(filename);
	    sc.scan();
	    sc.pAllMap();
	}
	
}

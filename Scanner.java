<<<<<<< HEAD
import java.io.*;
import java.util.*;

public class Scanner {
//	static ArrayList<String> tokenBuf = new ArrayList<String>();
    Token tokens = new Token();
	 
    public void readTxt(String filename) {        
        try {
            //讀取檔案
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String inputLine;

            //逐行讀取
            while (br.ready()) {
            	inputLine = br.readLine();
            	System.out.println(inputLine);
            	splitToken(inputLine);
            }
            
            fr.close();
        }catch (Exception e) {
        	System.out.println("[Error] "+filename+" can't be opened.\n");
        	e.printStackTrace();
        }
    }
    
    
    public void splitToken(String inputLine) {
    	int token_start = 0, token_end = 0;
//        System.out.println("inputLine.length: "+inputLine.length());
        
        while(token_start < inputLine.length()) {
            for(int i = token_start; i < inputLine.length(); i++) {
//            	System.out.println(inputLine.charAt(i));
//            	System.out.println("i = "+i);
            	
            	if(!Character.isLetterOrDigit(inputLine.charAt(i))) {
            		token_end = i;
            		break;
            	}
            }

//        	System.out.println("token_start:"+token_start);
//        	System.out.println("token_end:"+token_end);
        	
        	if(token_end-token_start == 0) {
                char buf;
                buf = inputLine.charAt(token_start);
                
            	if(buf!=' ') {
//            		System.out.println(buf);
            		tokens.addTokenBuf(Character.toString(buf));
            	}
	            token_start = token_end + 1;
        	} else {
	            char buf[] = new char[(token_end-token_start) + 1];
	            inputLine.getChars(token_start, token_end, buf, 0);
//	            System.out.println(buf);

        		tokens.addTokenBuf(String.valueOf(buf));
	            token_start = token_end;
        	}
//        	System.out.println(tokenBuf);
        }
        
        
//        		switch(token) {
//	        		case "include":
//	        			
//	        			break;
//	        		case "main":
//	        			break;
//	        		case "char":
//	        			break;
//	        		case "int":
//	        			break;
//	        		case "float":
//	        			break;
//	        		case "if":
//	        			break;
//	        		case "else":
//	        			break;
//	        		case "elseif":
//	        			break;
//	        		case "for":
//	        			break;
//	        		case "while":
//	        			break;
//	        		case "do":
//	        			break;
//	        		case "return":
//	        			break;
//	        		case "switch":
//	        			break;
//	        		case "case":
//	        			break;
//	        		case "printf":
//	        			break;
//	        		case "scanf":
//	        			break;
//        			default:
//        		}
//                
//                
//            }else {
//            	System.out.println("no");
//            }
    }
}

=======
import java.io.*;
import java.util.*;

public class Scanner {
//	static ArrayList<String> tokenBuf = new ArrayList<String>();
    Token tokens = new Token();
	 
    public void readTxt(String filename) {        
        try {
            //讀取檔案
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String inputLine;

            //逐行讀取
            while (br.ready()) {
            	inputLine = br.readLine();
            	System.out.println(inputLine);
            	splitToken(inputLine);
            }
            
            fr.close();
        }catch (Exception e) {
        	System.out.println("[Error] "+filename+" can't be opened.\n");
        	e.printStackTrace();
        }
    }
    
    
    public void splitToken(String inputLine) {
    	int token_start = 0, token_end = 0;
//        System.out.println("inputLine.length: "+inputLine.length());
        
        while(token_start < inputLine.length()) {
            for(int i = token_start; i < inputLine.length(); i++) {
//            	System.out.println(inputLine.charAt(i));
//            	System.out.println("i = "+i);
            	
            	if(!Character.isLetterOrDigit(inputLine.charAt(i))) {
            		token_end = i;
            		break;
            	}
            }

//        	System.out.println("token_start:"+token_start);
//        	System.out.println("token_end:"+token_end);
        	
        	if(token_end-token_start == 0) {
                char buf;
                buf = inputLine.charAt(token_start);
                
            	if(buf!=' ') {
//            		System.out.println(buf);
            		tokens.addTokenBuf(Character.toString(buf));
            	}
	            token_start = token_end + 1;
        	} else {
	            char buf[] = new char[(token_end-token_start) + 1];
	            inputLine.getChars(token_start, token_end, buf, 0);
//	            System.out.println(buf);

        		tokens.addTokenBuf(String.valueOf(buf));
	            token_start = token_end;
        	}
//        	System.out.println(tokenBuf);
        }
        

        
//        		switch(token) {
//	        		case "include":
//	        			
//	        			break;
//	        		case "main":
//	        			break;
//	        		case "char":
//	        			break;
//	        		case "int":
//	        			break;
//	        		case "float":
//	        			break;
//	        		case "if":
//	        			break;
//	        		case "else":
//	        			break;
//	        		case "elseif":
//	        			break;
//	        		case "for":
//	        			break;
//	        		case "while":
//	        			break;
//	        		case "do":
//	        			break;
//	        		case "return":
//	        			break;
//	        		case "switch":
//	        			break;
//	        		case "case":
//	        			break;
//	        		case "printf":
//	        			break;
//	        		case "scanf":
//	        			break;
//        			default:
//        		}
//                
//                
//            }else {
//            	System.out.println("no");
//            }
    }
}

>>>>>>> parent of 3723b09 (test)
//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
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
    	ArrayList<String> tokensTmp = new ArrayList<String>();
    	int token_start = 0, token_end = 0;
//        System.out.println("inputLine.length: "+inputLine.length());
    
    	// 
        while(token_start < inputLine.length()) {
         
        	int i;
            for(i = token_start; i < inputLine.length(); i++) {
            	
            	if(!Character.isLetterOrDigit(inputLine.charAt(i))) {
            		token_end = i;
            		break;
            	}
            }
//        	System.out.println("token_start:"+token_start);
//        	System.out.println("token_end:"+token_end);
        	
        	if(token_end == token_start) {
                char buf;
                buf = inputLine.charAt(token_start);
                
            	if(buf!=' ') {
    	            tokensTmp.add(Character.toString(buf));
            	}
            	
	            token_start++;
        	} else if (i == inputLine.length()) {
            	token_end = i-1;

	            char buf[] = new char[i-token_start];
	            inputLine.getChars(token_start, i, buf, 0);
	            tokensTmp.add(String.valueOf(buf));
	            token_start = i;
        	} else {
	            char buf[] = new char[token_end-token_start];
	            inputLine.getChars(token_start, token_end, buf, 0);
	            tokensTmp.add(String.valueOf(buf));
	            token_start = token_end;
        	}

        }
		tokens.addTokenBuf(tokensTmp);
    }    
        
    
    public void scan() {
    	for(int i=0 ; i<tokens.getTokenBufSize() ; i++) {
        	for(int j=0; j <tokens.getTokenBuf(i).size() ; j++) {
        		
//        		System.out.println(tokens.getOneTokenBuf(i, j));
        		if(j==0) {
        			switch(tokens.getOneTokenBuf(i, j)) {
	        			case "#":
	        	    		System.out.println(tokens.getOneTokenBuf(i, j+1));
	        				if(tokens.getOneTokenBuf(i, j+1)=="include") {
	        					includeType(tokens.getTokenBuf(i));
	        				}
	        				break;
        			}
        			
        		}
        	}
    	}
    }	
    
    public void includeType(ArrayList<String> tokens) {
//    	String regex1 = "include";
//    	Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
    	for(int i=2 ; i<=6 ; i++) {
    		tokens.get(i);
    		System.out.println(tokens.get(i));
    	}
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

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
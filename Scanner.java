import java.io.*;
import java.util.*;
import java.util.regex.*; 

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

    Pattern ptn_hashtag  = Pattern.compile("#", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_include = Pattern.compile("include", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_main = Pattern.compile("main", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_char = Pattern.compile("char", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_int = Pattern.compile("int", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_float = Pattern.compile("float", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_if = Pattern.compile("if", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_else = Pattern.compile("else", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_elseif = Pattern.compile("elseif", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_for = Pattern.compile("for", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_while = Pattern.compile("while", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_do = Pattern.compile("do", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_return = Pattern.compile("return", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_switch = Pattern.compile("switch", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_case = Pattern.compile("case", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_printf = Pattern.compile("printf", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_scanf = Pattern.compile("scanf", Pattern.CASE_INSENSITIVE); 

    public void scan() {
		int state;
    	for(int i=0 ; i<tokens.getTokenBufSize() ; i++) {
        	for(int j=0; j <tokens.getTokenBuf(i).size() ; j++) {
        		
        		String tkn_tmp		= tokens.getOneTokenBuf(i, j);
        		Matcher mat_hashtag	= ptn_hashtag.matcher(tkn_tmp);
        		Matcher mat_main	= ptn_main.matcher(tkn_tmp);
        		Matcher mat_char	= ptn_char.matcher(tkn_tmp);
        		Matcher mat_int		= ptn_int.matcher(tkn_tmp);
        		Matcher mat_float	= ptn_float.matcher(tkn_tmp);
        		Matcher mat_if		= ptn_if.matcher(tkn_tmp);
        		Matcher mat_else 	= ptn_else.matcher(tkn_tmp);
        		Matcher mat_elseif 	= ptn_elseif.matcher(tkn_tmp);
        		Matcher mat_for 	= ptn_for.matcher(tkn_tmp);
        		Matcher mat_while 	= ptn_while.matcher(tkn_tmp);
        		Matcher mat_do 		= ptn_do.matcher(tkn_tmp);
        		Matcher mat_return 	= ptn_return.matcher(tkn_tmp);
        		Matcher mat_switch 	= ptn_switch.matcher(tkn_tmp);
        		Matcher mat_case 	= ptn_case.matcher(tkn_tmp);
        		Matcher mat_printf 	= ptn_printf.matcher(tkn_tmp);
        		Matcher mat_scanf 	= ptn_scanf.matcher(tkn_tmp);

        		if(mat_hashtag.matches())
        			state = 0;
        		else if(mat_main.matches())
        			state = 1;
        		else if(mat_char.matches()) 
        			state = 2;
        		else if(mat_int.matches()) 
        			state = 3;
        		else if(mat_float.matches())
        			state = 4;
        		else if(mat_if.matches())
        			state = 5;
        		else if(mat_else.matches()) 
        			state = 6;
        		else if(mat_elseif.matches())
        			state = 7;
        		else if(mat_for.matches()) 
        			state = 8;
        		else if(mat_while.matches())
        			state = 9;
        		else if(mat_do.matches())
        			state = 10;
        		else if(mat_return.matches())
        			state = 11;
        		else if(mat_switch.matches())
        			state = 12;
                else if(mat_case.matches())
                	state = 13;
                else if(mat_printf.matches()) 
                	state = 14;
                else if(mat_scanf.matches())
                	state = 15;
                else 
                	state = 16;
                
        		
        		if(j==0) {
        			switch(state) {
        				// #
	        			case 0:
	        				// 判斷#後面是否為include
	        				Matcher mat_include = ptn_include.matcher(tokens.getOneTokenBuf(i, j+1));
	        				if(mat_include.matches()) {
	        					
	        					// 合併<xxx.h>
	        					String library_tmp = "";
	        					for(int k=2 ; k<=6 ; k++)
	        						library_tmp = library_tmp + tokens.getTokenBuf(i).get(k);
	        					
	        					// 判斷#include後面是否為<xxx.h>
	        					Matcher mat_libname = ptn_libname.matcher(library_tmp);
	        					if(mat_libname.matches()) {
	        						
	        						// token格式為:#include<xxx.h>
	        						tokens.addRWMap("#");
	        						tokens.addRWMap("include");
	        						tokens.addRWMap(library_tmp);
	        						// j 移至下一行
	        						j = tokens.getTokenBuf(i).size();
	        					}
	        				}
	        				break;
        				// main
	        			case 1:
	        				break;
        			}
        			
        		} else {
        			
        		}
        	}
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
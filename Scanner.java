import java.io.*;
import java.util.*;
import java.util.regex.*; 

public class Scanner {
	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	private static ArrayList<ArrayList<String>> tokenBuf_space = new ArrayList<>(2);

	private static Token tokens = new Token();
	
	private static Token reservedWord = new Token();
	private static Token libraryName = new Token();
	private static Token identifier = new Token();
	private static Token character = new Token();
	private static Token number = new Token();
	private static Token pointer = new Token();
	private static Token bracket = new Token();
	private static Token operator = new Token();
	private static Token comparator = new Token();
	private static Token address = new Token();
	private static Token punctuation = new Token();
	private static Token formatSpecifier = new Token();
	private static Token printedToken = new Token();
	private static Token comment = new Token();
	private static Token undefinedToken = new Token();
	private static Token skippedToken = new Token();
    
    
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
    	ArrayList<String> tokensTmp_space = new ArrayList<String>();
    	int token_start = 0, token_end = 0;
//        System.out.println("inputLine.length: "+inputLine.length());

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

            	if(buf!=' ')
    	            tokensTmp.add(Character.toString(buf));
                tokensTmp_space.add(Character.toString(buf));
            	
	            token_start++;
        	} else if (i == inputLine.length()) {
            	token_end = i-1;

	            char buf[] = new char[i-token_start];
	            inputLine.getChars(token_start, i, buf, 0);
	            tokensTmp.add(String.valueOf(buf));
                tokensTmp_space.add(String.valueOf(buf));
	            token_start = i;
        	} else {
	            char buf[] = new char[token_end-token_start];
	            inputLine.getChars(token_start, token_end, buf, 0);
	            tokensTmp.add(String.valueOf(buf));
                tokensTmp_space.add(String.valueOf(buf));
	            token_start = token_end;
        	}

        }
        tokenBuf.add(tokensTmp);
		tokenBuf_space.add(tokensTmp_space);
		System.out.println(tokensTmp);
    }

    
    // 印出ArrayList-tokenBuf
    public void coutTokenBuf() {
    	for(int i=0 ; i<tokenBuf.size() ; i++)
    		System.out.println(tokenBuf.get(i));
    }

    
    // 取得ArrayList-tokenBuf的第(x, y)位
    public String getOneTokenBuf(int x, int y) {
    	return String.valueOf(tokenBuf.get(x).get(y));
    }
    
    
    public void pAllMap() {
		System.out.println("reservedWord:");
    	reservedWord.pMap();
		System.out.println("libraryName:");
    	libraryName.pMap();
		System.out.println("identifier:");
    	identifier.pMap();
		System.out.println("character:");
    	character.pMap();
		System.out.println("number:");
    	number.pMap();
		System.out.println("pointer:");
    	pointer.pMap();
		System.out.println("bracket:");
    	bracket.pMap();
		System.out.println("operator:");
    	operator.pMap();
		System.out.println("comparator:");
    	comparator.pMap();
		System.out.println("address:");
    	address.pMap();
		System.out.println("punctuation:");
    	punctuation.pMap();
		System.out.println("formatSpecifier:");
    	formatSpecifier.pMap();
		System.out.println("printedToken:");
    	printedToken.pMap();
		System.out.println("comment:");
    	comment.pMap();
		System.out.println("undefinedToken:");
    	undefinedToken.pMap();
		System.out.println("skippedToken:");
    	skippedToken.pMap();
    	
    }
    
    
    Pattern ptn_hashtag  = Pattern.compile("#"); 
    Pattern ptn_include = Pattern.compile("include", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)"); 
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

    Pattern ptn_identifier = Pattern.compile("([a-zA-Z]+)([a-zA-Z0-9]*)"); 
    
    public void scan() {
		int state;
		
		boolean bool_punctuation = false;
		
    	for(int i=0 ; i<tokenBuf.size() ; i++) {
        		
    		String tkn_tmp		= getOneTokenBuf(i, 0);
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
                
        		
    			switch(state) {
    			
    				// #
        			case 0:
        				punctuation.addMap(tkn_tmp);
        				// 判斷#後面是否為include
        				String include_tmp = getOneTokenBuf(i, 1);
        				Matcher mat_include = ptn_include.matcher(include_tmp);
        				
        				if(mat_include.matches()) {
        					// 合併<xxx.h>
        					String library_tmp = "";
        					for(int k=2 ; k<=6 ; k++)
        						library_tmp = library_tmp + tokenBuf.get(i).get(k);
        					
        					// 判斷#include後面是否為<xxx.h>
        					Matcher mat_libname = ptn_libname.matcher(library_tmp);
        					if(mat_libname.matches()) {
        						
        						// token格式為:#include<xxx.h>
        						reservedWord.addMap("include");
        						libraryName.addMap(library_tmp);
        					} else {
        						undefinedToken.addMap(tokenBuf.get(i).get(2));
        						String skipTokens = "";
        						int j = 3;
        						while(j < tokenBuf_space.get(i).size()) {
            						skipTokens = skipTokens + tokenBuf.get(i).get(j);
        							j++;
        						}
        						skippedToken.addMap(skipTokens);
        					}
        				} else {
        					undefinedToken.addMap(include_tmp);
        				}
        				break;
        				
    				// main
        			case 1:
        				break;
        				
        			// char
        			case 2:
						tokens.addMap(tkn_tmp);
        				bool_punctuation = false;
    					for(int j=1 ; j<tokenBuf.get(i).size() ; j++) {
    						String tk = getOneTokenBuf(i, j); 
    		        		if (!bool_punctuation) {

        		        		Matcher mat_identifier = ptn_identifier.matcher(tk);
	    		        		if (mat_identifier.matches()) {
	    		        			
	    		        			tokens.addMap(tk);
	    		        			bool_punctuation = true;
	    		        			
	    		        		} else if (tk.equals("*")) {
	    		        			
	        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
	    		        			if(mat_identifi.matches()) {
		    		        			String pointer_tmp = "";
	            						pointer_tmp = tk + getOneTokenBuf(i, j+1);
		    		        			tokens.addMap(pointer_tmp);
		        		        		j++;
		    		        			bool_punctuation = true;
	    		        			}
	    		        			
	    		        		} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
	    		        		
    		        		} else {
    		        			
    		        			if (tk.equals(",") || tk.equals(";")) {
    		        				bool_punctuation = false;
	    		        			tokens.addMap(tk);
    		        			} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
    		        			
    		        		}
    					}
        				break;
        				
        			// int
        			case 3:
						tokens.addMap(tkn_tmp);
        				bool_punctuation = false;
    					for(int j=1 ; j<tokenBuf.get(i).size() ; j++) {
    						String tk = getOneTokenBuf(i, j); 
    		        		if(tk ==" ") break;
    		        		
    		        		if (!bool_punctuation) {

        		        		Matcher mat_identifier = ptn_identifier.matcher(tk);
        		        		if (mat_identifier.matches()) {
	    		        			
	    		        			tokens.addMap(tk);
	    		        			bool_punctuation = true;
	    		        			
	    		        		} else if (tk.equals("*")) {
	    		        			
	        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
	    		        			if(mat_identifi.matches()) {
		    		        			String pointer_tmp = "";
	            						pointer_tmp = tk + getOneTokenBuf(i, j+1);
		    		        			tokens.addMap(pointer_tmp);
		        		        		j++;
		    		        			bool_punctuation = true;
	    		        			}
	    		        			
	    		        		} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
	    		        		
    		        		} else {
    		        			
    		        			if (tk.equals(",") || tk.equals(";")) {
    		        				bool_punctuation = false;
	    		        			tokens.addMap(tk);
    		        			} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
    		        			
    		        		}
    					}
    					
        				break;
        				
    				// float
        			case 4:
						tokens.addMap(tkn_tmp);
        				bool_punctuation = false;
    					for(int j=1 ; j<tokenBuf.get(i).size() ; j++) {
    						String tk = getOneTokenBuf(i, j); 
    		        		
    		        		if (!bool_punctuation) {

        		        		Matcher mat_identifier = ptn_identifier.matcher(tk);
        		        		if (mat_identifier.matches()) {
	    		        			
	    		        			tokens.addMap(tk);
	    		        			bool_punctuation = true;
	    		        			
	    		        		} else if (tk.equals("*")) {
	    		        			
	        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
	    		        			if(mat_identifi.matches()) {
		    		        			String pointer_tmp = "";
	            						pointer_tmp = tk + getOneTokenBuf(i, j+1);
		    		        			tokens.addMap(pointer_tmp);
		        		        		j++;
		    		        			bool_punctuation = true;
	    		        			}
	    		        			
	    		        		} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
	    		        		
    		        		} else {

    		        			if (tk.equals(",") || tk.equals(";")) {
    		        				bool_punctuation = false;
	    		        			tokens.addMap(tk);
    		        			} else {
	        						tokens.addMap("undefined token");
        							j++;
	        						while(j < tokenBuf.get(i).size()) {
		        						tokens.addMap("skip token");
	        							j++;
	        						}
	    		        		}
    		        			
    		        		}
    					}
    					
        				break;
        				
    				// if
        			case 5:
        				break;
        				
    				// else 
        			case 6:
        				break;
        				
    				// elseif 
        			case 7:
        				break;
        				
    				// for 
        			case 8:
        				break;
        				
    				// while 
        			case 9:
        				break;
        				
    				// do 
        			case 10:
        				break;
        				
    				// return 
        			case 11:
        				break;
        				
    				// switch 
        			case 12:
        				break;
        				
    				// case 
        			case 13:
        				break;
        				
    				// printf 
        			case 14:
        				break;
        				
    				// scanf
        			case 15:
        				break;
        				
        			default:
    			}
    	}
    }
}

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
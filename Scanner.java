import java.io.*;
import java.util.*;
import java.util.regex.*; 

public class Scanner {
	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	private static ArrayList<ArrayList<String>> tokenBuf_space = new ArrayList<>(2);

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
//            	System.out.println(inputLine);
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

        while(token_start < inputLine.length()) {
        	int i;
            for(i = token_start; i < inputLine.length(); i++) {
            	
            	if(!Character.isLetterOrDigit(inputLine.charAt(i))) {
            		token_end = i;
            		break;
            	}
            }
        	
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
        tokenBuf.add(tokensTmp_space);
		tokenBuf_space.add(tokensTmp_space);
		System.out.println(tokensTmp_space);
    }

     
    Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)"); 

    public void scan() {
		int state = 0;

		boolean bool_endLine = true;
		boolean bool_punctuation = false;
		boolean bool_bracket = false;
		
    	for(int i=0 ; i<tokenBuf.size() ; i++) {
    		bool_endLine = true;
    		
    		for(int j=0 ; j<tokenBuf.get(i).size() ; j++) {
    			String tkn = tokenBuf.get(i).get(j);
    			if(tkn.equals(" ")) continue;
//    			System.out.println("i: "+i+", j:"+j);
//				System.out.println("token now(略過空格): "+tkn);
    			
				
    			if(bool_endLine) {
	    			bool_endLine = false;

	        		if(compareString("#", tkn)) {
	        			state = 0;
        				punctuation.addMap(tkn);
						System.out.println("token "+tkn+" belongs to punctuation");
						
	        		}
	        		else if(compareString("main", tkn)) {
	        			state = 1;
	    	        	System.out.println("token "+tkn+" not classified yet");
	        			
	        		}
	        		else if(compareString("char", tkn)) {
	        			state = 2;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
						
	        		}
	        		else if(compareString("int", tkn)) {
	        			state = 3;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
						
	        		}
	        		else if(compareString("float", tkn)) {
	        			state = 4;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
						
	        		}
	        		else if(compareString("if", tkn)) {
	        			state = 5;
	    	        	System.out.println("token "+tkn+" not classified yet");
	        			
	        		}
	        		else if(compareString("for", tkn))  {
	        			state = 6;
	        			reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
	        			
	        		}
	        		else if(compareString("while", tkn))  {
	        			state = 7;
	    	        	System.out.println("token "+tkn+" not classified yet");
	        			
	        		}
	        		else if(compareString("do", tkn))  {
	    				state = 8;
	    	        	System.out.println("token "+tkn+" not classified yet");
	    				
	        		}
	    			else if(compareString("return", tkn))  {
	    				state = 9;
	    	        	System.out.println("token "+tkn+" not classified yet");
	    				
	    			}
	    			else if(compareString("switch", tkn))  {
	    				state = 10;
	    	        	System.out.println("token "+tkn+" not classified yet");
	    				
	    			}
	    	        else if(compareString("case", tkn))  {
	    	        	state = 11;
	    	        	System.out.println("token "+tkn+" not classified yet");
	    	        	
	    	        }
	    	        else if(compareString("printf", tkn))   {
	    	        	state = 12;
	    	        	bool_bracket = false;
	    	        	reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
	    	        	
	    	        }
	    	        else if(compareString("scanf", tkn))  {
	    	        	state = 13;
	    	        	bool_bracket = false;
	    	        	reservedWord.addMap(tkn);
						System.out.println("token "+tkn+" belongs to reserved word");
	    	        	
	    	        }
	    	        else {
	    	        	state = 14;
	    	        	System.out.println("token "+tkn+" not classified yet");
	    	        	
	    	        }
	        		continue;
	    			}
    			

    			switch(state) {
    			
    				// #
        			case 0:
        				// 判斷#後面是否為include
        				if(compareString("include", tkn)) {

        					// #後面是include
    						reservedWord.addMap("include");
    						System.out.println("token include belongs to reserved word");

        					// 合併<xxx.h>
        					String library_tmp = "";
        					int k;
        					for(k=2 ; k<=6 ; k++)
        						library_tmp = library_tmp + tokenBuf.get(i).get(k);
        					
        					// 判斷#include後面是否為<xxx.h>
        					Matcher mat_libname = ptn_libname.matcher(library_tmp);
        					if(mat_libname.matches()) {
        						// token格式為:<xxx.h>
        						libraryName.addMap(library_tmp);
        						System.out.println("token "+library_tmp+" belongs to library name");
        						
        						// 結束換下一行
        						j = k;
        						bool_endLine = true;
        					} else {
        						// library格式不正確
        						comparator.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to comparator");

        						// 取得並分類undefinedToken
        						String undefinedTokens = "";
        						int f = 3;
        						while(f < 6 && f < tokenBuf_space.get(i).size()) {
        							undefinedTokens = undefinedTokens + tokenBuf_space.get(i).get(f);
        							f++;
        						}
        						undefinedToken.addMap(undefinedTokens);
        						System.out.println("token "+undefinedTokens+" belongs to undefined token");
        						state = 14;
        					
        					}
        				} else {
        					undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
        				}
        				break;
        				
    				// main
        			case 1:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
        			// char
        			case 2:
		        		if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to identifier token");
    		        			bool_punctuation = true;
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						identifier.addMap(pointer_tmp);
            						System.out.println("token "+pointer_tmp+" belongs to identifier token");
	    		        			bool_punctuation = true;
	    		        			
    		        			}
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to undefined token");
        						state = 14;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
		        		}
        				break;
        				
        			// int
        			case 3:
		        		if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to identifier token");
    		        			bool_punctuation = true;
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						identifier.addMap(pointer_tmp);
            						System.out.println("token "+pointer_tmp+" belongs to identifier token");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to undefined token");
        						state = 14;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
		        		}
        				break;
        				
    				// float
        			case 4:
		        		if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to identifier token");
    		        			bool_punctuation = true;
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						identifier.addMap(pointer_tmp);
            						System.out.println("token "+pointer_tmp+" belongs to identifier token");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to undefined token");
        						state = 14;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
		        		}
        				break;
        				
    				// if
        			case 5:
        				if(tkn.equals("(")){
        					bracket.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to bracket token");
        					
        				}
        				break;
        				
    				// for 
        			case 6:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// while 
        			case 7:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// do 
        			case 8:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// return 
        			case 9:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// switch 
        			case 10:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// case 
        			case 11:
	    	        	System.out.println("token "+tkn+" not classified yet");
        				break;
        				
    				// printf 
        			case 12:
        				if(!bool_bracket && tkn.equals("(")) {
	    					bool_bracket = true;
		        			bracket.addMap(tkn);
							System.out.println("token "+tkn+" belongs to bracket token");
	        			
        				} else if(bool_bracket){
        					if (compareIdentifier(tkn)) {
    		        			if(tokenBuf.get(i).get(j-2).equals(",")) {
    		        				identifier.addMap(tkn);
    	    						System.out.println("token "+tkn+" belongs to identifier token");
    	    						
    		        			} else {
    		        				printedToken.addMap(tkn);
    	    						System.out.println("token "+tkn+" belongs to printed token");
    	    						
    		        			}
    		        		} else if (tkn.equals("%")||tkn.equals("\\")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
    		        				String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
            						formatSpecifier.addMap(pointer_tmp);
    	    						System.out.println("token "+pointer_tmp+" belongs to format specifier token");
            		        		j++;
            		        		
    		        			} else {
    			        			undefinedToken.addMap(tkn);
    	    						System.out.println("token "+tkn+" belongs to undefined token");
    	    						state = 14;
    	    						
    			        		}
    		        		}else if (tkn.equals(",") || tkn.equals("\"")) {
    		        			punctuation.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to punctuation token");
        						
    		        		}else if(tkn.equals(")")) {
    	    					bool_bracket = false;
    		        			bracket.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to bracket token");
    		        			
    		        		}else {
    		        			undefinedToken.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to undefined token");
        						state = 14;
        						
    		        		}
        				} else if (tkn.equals(";")) {
		        			punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
    						
		        		} else if(tkn.equals("/")) {
		        			String sa ="";
		        			
		        			if(tokenBuf.get(i).get(j+1).equals("/") || tokenBuf.get(i).get(j+1).equals("*")) {
		        				while(j < tokenBuf_space.get(i).size())
    		        				sa += tokenBuf.get(i).get(j++);
    		        			comment.addMap(sa);
        						System.out.println("token "+sa+" belongs to comment token");
        						
		        			} else {
			        			undefinedToken.addMap(tkn);
	    						System.out.println("token "+tkn+" belongs to undefined token");
	    						state = 14;
	    						
			        		}
		        		} else {
		        			undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
    						
		        		}
        				break;
        				
    				// scanf
        			case 13:
        				if(!bool_bracket && tkn.equals("(")) {
        					bool_bracket = true;
		        			bracket.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to bracket token");
		        			
        				} else if(bool_bracket){
        					boolean undefined = false;
        					if (tkn.equals("%")) {
        						
    	        				String s = tokenBuf.get(i).get(j+1);
    	        				char fst = s.charAt(0);
    	        				String pointer_tmp = tkn + fst;
    	        				String a = "";
    	        				
    	        				for(int k=0 ; k<s.length() ; k++) {
    	        					if(k==0 && compareString("([cdf])", String.valueOf(fst)) ) {
    	        						formatSpecifier.addMap(pointer_tmp);
    	        						System.out.println("token "+pointer_tmp+" belongs to format specifier token");
    	        					
    	        					} else { 
    	        						undefined = true;
    	        						a +=s.charAt(k);
	        						}
    	        				}
        						j++;
    	        				
    	        				if(undefined) {
	    	        				undefinedToken.addMap(a);
	        						System.out.println("token "+a+" belongs to undefined token");
	        						state = 14;
    	        				}
    		        		}else if(tkn.equals("&")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
    		        				String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
            						address.addMap(pointer_tmp);
            						System.out.println("token "+pointer_tmp+" belongs to address token");
            		        		j++;
            		        		
    		        			} else {
    			        			undefinedToken.addMap(tkn);
    	    						System.out.println("token "+tkn+" belongs to undefined token");
    	    						state = 14;
    			        		}
    		        		}else if (tkn.equals(",") || tkn.equals(";") || tkn.equals("\"")) {
    		        			punctuation.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to punctuation token");
    		        			
    		        		}else if(tkn.equals("/")){
    		        			String sa ="";
    		        			
    		        			if(tokenBuf.get(i).get(j+1).equals("/")||tokenBuf.get(i).get(j+1).equals("*")) {
    		        				while(j < tokenBuf_space.get(i).size())
        		        				sa += tokenBuf.get(i).get(j++);
        		        			comment.addMap(sa);
            						System.out.println("token "+sa+" belongs to comment token");
        		        			
    		        			} else {
    			        			undefinedToken.addMap(tkn);
    	    						System.out.println("token "+tkn+" belongs to undefined token");
    	    						state = 14;
    			        		}	
    		        		}else if(tkn.equals(")")) {
    		        			bracket.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to bracket token");
    		        			bool_bracket = false;
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to undefined token");
        						state = 14;
    		        		}
        					
        				} else if (tkn.equals(";")) {
		        			punctuation.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to punctuation token");
    						
		        		} else if(tkn.equals("/")) {
		        			String sa ="";
		        			
		        			if(tokenBuf.get(i).get(j+1).equals("/") || tokenBuf.get(i).get(j+1).equals("*")) {
		        				while(j < tokenBuf_space.get(i).size())
    		        				sa += tokenBuf.get(i).get(j++);
    		        			comment.addMap(sa);
        						System.out.println("token "+sa+" belongs to comment token");
        						
		        			} else {
			        			undefinedToken.addMap(tkn);
	    						System.out.println("token "+tkn+" belongs to undefined token");
	    						state = 14;
	    						
			        		}
		        		} else {
		        			undefinedToken.addMap(tkn);
    						System.out.println("token "+tkn+" belongs to undefined token");
    						state = 14;
		        		}
        				break;
        			
        			// skipped
        			case 14:
						String skip_tmp = ""; 
						while(j < tokenBuf.get(i).size()) {
							skip_tmp = skip_tmp + tokenBuf.get(i).get(j);
    						j++;
						}

						skippedToken.addMap(skip_tmp);
						System.out.println("token "+skip_tmp+" belongs to skipped token");
						bool_endLine = true;
        				break;
        				
        			default:
    			}
    		}
    	}
    }
    
    
    public boolean compareString(String sA, String sB) {	// sA放正確的字串 sB放需要被比字串
    	Pattern ptn = Pattern.compile(sA, Pattern.CASE_INSENSITIVE); 
    	Matcher mat	= ptn.matcher(sB);
    	if(mat.matches())
    		return true;
    	else
    		return false;
    }
    
    
    
    
    public boolean compareIdentifier(String S) {	// sA放正確的字串 sB放需要被比字串
        Pattern ptn = Pattern.compile("([a-zA-Z])([a-zA-Z0-9]*)"); 
    	Matcher mat	= ptn.matcher(S);
    	if(mat.matches())
    		return true;
    	else
    		return false;
    }
    
    
    
    
    public void pAllMap() {
    	System.out.println(" ");
    	System.out.println("----  ----  ----  ----  ----  ----  ----  ----  ----  ----  ----");
    	System.out.println(" ");
		System.out.print("reservedWord:\t");
    	reservedWord.pMap();
    	System.out.println(" ");
		System.out.print("libraryName:\t");
    	libraryName.pMap();
    	System.out.println(" ");
		System.out.print("identifier:\t");
    	identifier.pMap();
    	System.out.println(" ");
		System.out.print("character:\t");
    	character.pMap();
    	System.out.println(" ");
		System.out.print("number:\t\t");
    	number.pMap();
    	System.out.println(" ");
		System.out.print("pointer:\t");
    	pointer.pMap();
    	System.out.println(" ");
		System.out.print("bracket:\t");
    	bracket.pMap();
    	System.out.println(" ");
		System.out.print("operator:\t");
    	operator.pMap();
    	System.out.println(" ");
		System.out.print("comparator:\t");
    	comparator.pMap();
    	System.out.println(" ");
		System.out.print("address:\t");
    	address.pMap();
    	System.out.println(" ");
		System.out.print("punctuation:\t");
    	punctuation.pMap();
    	System.out.println(" ");
		System.out.print("formatSpecifier:");
    	formatSpecifier.pMap();
    	System.out.println(" ");
		System.out.print("printedToken:\t");
    	printedToken.pMap();
    	System.out.println(" ");
		System.out.print("comment:\t");
    	comment.pMap();
    	System.out.println(" ");
		System.out.print("undefinedToken:\t");
    	undefinedToken.pMap();
    	System.out.println(" ");
		System.out.print("skippedToken:\t");
    	skippedToken.pMap();
    	System.out.println(" ");
    	
    }
}

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
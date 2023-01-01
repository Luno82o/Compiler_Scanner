import java.io.*;
import java.util.*;
import java.util.regex.*; 

public class Scanner {
	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	
	static Output op = new Output();
	
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

	private static String Judgement = "";

	private ArrayList<String> operator_form = new ArrayList<String>(){
		{
			add("+");add("-");add("*");add("/");add("++");add("--");
			add("%");add("^");add("&");add("|");add("=");
		}
	};
	
	int bracket_check = 0;
	private ArrayList<String> bracket_form = new ArrayList<String>(){
		{
			add("(");add(")");add("{");add("}");add("[");add("]");
		}
	};
	
	private ArrayList<String> punctuation_form = new ArrayList<String>(){
		{
			add(",");add(";");add(":");add("#");add("\"");add("\'");
		}
	};
	
	private ArrayList<String> comparator_form = new ArrayList<String>(){
		{
			add(">");add("<");add(">=");add("<=");add("!=");add("==");
		}
	};
	
	// private ArrayList<String> punctuation_form = new ArrayList<String>(){
	// 	{
	// 		add(";");add(",");add("#");
	// 	}
	// };
    
    
    public void readTxt(String filename) {        
        try {
            //讀取檔案
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String inputLine;

            //逐行讀取
            while (br.ready()) {
            	inputLine = br.readLine();
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
//		System.out.println(tokensTmp_space);
    }


    Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)"); 
    Pattern ptn_hname = Pattern.compile("([a-zA-Z]+)(.h)"); 

    Pattern ptn_identifier = Pattern.compile("([a-zA-Z])([a-zA-Z0-9]*)"); 

	Pattern ptn_number = Pattern.compile("-?\\d+");

	//Pattern ptn_operator = Pattern.compile("([*/%^[+{1,2}][-{1,2}]&|=])");
    

    public void scan() {
		int state = 0;
		
		boolean bool_endLine = true;
		boolean bool_punctuation = false;
		boolean bool_bracket = false;
		boolean bool_case = false;
		boolean bool_double = false;

		boolean bool_doWhile = false;
		boolean bool_elseif = false;
		boolean bool_else = false;

		// 單引號
		String char_token = "";
		
		String sa ="";
		
		Matcher mat_number;
		
    	for(int i=0 ; i<tokenBuf.size() ; i++) {
    		bool_endLine = true;
    		
    		for(int j=0 ; j<tokenBuf.get(i).size() ; j++) {
    			String tkn = tokenBuf.get(i).get(j);
    			if(tkn.equals(" ")) continue;
    			
    			if(bool_endLine) {
	    			bool_endLine = false;
	    			
	        		if(compareString("#", tkn)) {
	        			state = 0;
        				punctuation.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Punctuation");
						
	        		}
	        		else if(compareString("main", tkn)) {
	        			state = 1;
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	        		}
	        		else if(compareString("char", tkn)) {
	        			state = 2;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	        		}
	        		else if(compareString("int", tkn)) {

	        			state = 3;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");

	        		}
	        		else if(compareString("float", tkn)) {
	        			state = 4;
        				bool_punctuation = false;
	        			reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	        		}
	        		else if(compareString("if", tkn)) {
	        			state = 5;

	        			bool_bracket = true;
						bool_elseif = true;
						bool_else = true;
						bool_punctuation = false;

						reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");

	        		}
	        		else if(compareString("for", tkn))  {
	        			state = 6;
	        			reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	        			
						bool_bracket = true;
						bool_punctuation = false;
	        		}
	        		else if(compareString("while", tkn))  {
	        			state = 7;
						reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
						
						bool_bracket = true;
						bool_punctuation = false;

	        		}
	        		else if(compareString("do", tkn))  {
	    				state = 8;
						reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    				
						bool_bracket = true;
						bool_punctuation = false;

	        		}
	    			else if(compareString("return", tkn))  {
	    				state = 9;
						reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    				
	    			}
	    			else if(compareString("switch", tkn)) {
	    				state = 10;
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    				
	    			}
	    	        else if(compareString("case", tkn))  {
	    	        	state = 11;
	    	        	bool_case = true;
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    	        	
	    	        }
	    	        else if(compareString("printf", tkn))   {
	    	        	state = 12;
	    	        	bool_bracket = false;
	    	        	bool_double = false;
	    	        	reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    	        	
	    	        }
	    	        else if(compareString("scanf", tkn)) {
	    	        	state = 13;
	    	        	bool_bracket = false;
	    	        	bool_double = false;
	    	        	reservedWord.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
	    	        	
	    	        } else if (tkn.equals("*")) {
	        			String pointer_tmp = "";
	    	        	pointer_tmp = tkn + tokenBuf.get(i).get(++j);
	    	        	
	        			if(pointer.token_defined(pointer_tmp)) {
	        				state = 14;
	        				pointer.addMap(pointer_tmp);
							Judgement += op.Judgement_Process_Line(pointer_tmp, "Pointer");
							
	        			} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
							state = 16;
		        		}
    				}
	    	        else if(identifier.token_defined(tkn)) {
						state = 14;
						identifier.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Identifier");
					}
	    	        else if(bool_bracket && tkn.equals("}")){
						bracket.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Bracket");
						
	    	        	bool_endLine = true;
					}
	        		else if(tkn.equals("/")) {
						state = 15;
	        			sa = "/";
	        			
        			}
					else if(tkn.equals("elseif")) {
						if(bool_elseif){
							state = 17;
							reservedWord.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");

						}
						else{
							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {

								skipped_token += tokenBuf.get(i).get(j);
								j++;

							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "Skipped_token");
						}
					}
					else if(tkn.equals("else")) {
						if(bool_elseif || bool_else){
							state = 18;
							reservedWord.addMap(tkn);
							bool_bracket = true;
							Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
						}
						else{
							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {

								skipped_token += tokenBuf.get(i).get(j);
								j++;

							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "Skipped_token");
						}
						
					}
					else if(tkn.equals("<")) {
						comparator.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "comparator");
						
    					String library_tmp = "";
    					for(int k=1 ; k<4 ; k++) 
							library_tmp = library_tmp + tokenBuf.get(i).get(j+k);

    					Matcher mat_hname = ptn_hname.matcher(library_tmp);
    					if(mat_hname.matches()) {
    						state = 16;
    						undefinedToken.addMap(library_tmp);
    						Judgement += op.Judgement_Process_Line(library_tmp, "Undefined_token");
							j = j + 3;
    					}else {
    						state = 16;
    						undefinedToken.addMap(tkn);
    						Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");

    	    	        }
					}
	        		
					else {
						state = 16;
						undefinedToken.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");

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
							Judgement += op.Judgement_Process_Line("include", "ReservedWord");

							do {
								j++;
							} while (tokenBuf.get(i).get(j).equals(" "));
        					
        					// 合併<xxx.h>
        					String library_tmp = "";
        					
        					for(int k=0 ; k<5 ; k++) {
    							library_tmp = library_tmp + tokenBuf.get(i).get(j+k);
    						}
        					
        					// 判斷#include後面是否為<xxx.h>
        					Matcher mat_libname = ptn_libname.matcher(library_tmp);
        					if(mat_libname.matches()) {
        						// token格式為:<xxx.h>
        						libraryName.addMap(library_tmp);
        						Judgement += op.Judgement_Process_Line(library_tmp, "Library_Name");
        						// 結束換下一行
        						j = j + 4;
        					} else {
        						// library格式不正確
//        						comparator.addMap(tkn);
//								Judgement += op.Judgement_Process_Line(tkn, "Comparator");

        						// 取得並分類undefinedToken
        						String undefinedTokens = "";
        						int f = 3;
        						while(f < 6 && f < tokenBuf.get(i).size()) {
        							if(tokenBuf.get(i).get(f).equals(" ")) break;
        							undefinedTokens = undefinedTokens + tokenBuf.get(i).get(f);
        							f++;
        						}
        						undefinedToken.addMap(undefinedTokens);
								Judgement += op.Judgement_Process_Line(undefinedTokens, "Undefined_token");
        						state = 16;
        					
        					}
        				} else {
        					undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
    						state = 16;
        				}
        				break;
        				
    				// main
        			case 1:
        				if(!bool_bracket && tkn.equals("(")) {
	    					bool_bracket = true;
		        			bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
        				}else if(bool_bracket){
        					if(tkn.equals(")")) {
    	    					bool_bracket = true;
    		        			bracket.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        		}else if(tkn.equals("{")) {
    	    					bool_bracket = false;
    		        			bracket.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        		}else {
    		        			undefinedToken.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "undefined");
        						state = 16;
    		        		}
        				}else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined");
    						state = 16;
		        		}
        				break;
        				
        			// char
        			case 2:
		        		if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "Identifier");
    		        			bool_punctuation = true;

    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";

            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						pointer.addMap(pointer_tmp);
        							Judgement += op.Judgement_Process_Line(pointer_tmp, "Pointer");
	    		        			bool_punctuation = true;
	    		        			
    		        			}
    		        		} else {
    		        			undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
        						state = 16;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");

		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
    						state = 16;
		        		}
        				break;
        				
        			// int
        			case 3:
        				if(tkn.equals("main")) {
        					reservedWord.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "reservedword");
		        			state = 1;
		        		} else if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
    		        			bool_punctuation = true;
								Judgement += op.Judgement_Process_Line(tkn, "Identifier");
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						pointer.addMap(pointer_tmp);
									Judgement += op.Judgement_Process_Line(pointer_tmp, "pointer");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
        						state = 16;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
    						state = 16;
		        		}
        				break;
        				
    				// float
        			case 4:
		        		if (!bool_punctuation) {
    		        		if (compareIdentifier(tkn)) {
    		        			identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "Identifier");
    		        			bool_punctuation = true;
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(++j);
            						pointer.addMap(pointer_tmp);
									Judgement += op.Judgement_Process_Line(pointer_tmp, "pointer");

	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
        						state = 16;
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");

		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
    						state = 16;
		        		}
        				break;
        				
    				// if
        			case 5:
						// meet identifier, operator, comparator, number
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >= compareString
							String back_char = tokenBuf.get(i).get(j+1);

							if( compareString("[+-=!<>]", tkn) && compareString("[+-=]", back_char) ){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){
								identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "identifier");
								

							}else if(operator_form.contains(tkn)){

								// if(tkn.equals("++") || tkn.equals("--")){
								// 	bool_bracket = true;
								// 	bool_punctuation = false;
								// }else{
								// 	bool_bracket = false;
								// 	bool_punctuation = false;
								// }
								operator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = false;
								bool_bracket = true;
								Judgement += op.Judgement_Process_Line(tkn, "number");

							}else{

								undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
							}
						}
						// meet punctuation
						else if(!bool_bracket && bool_punctuation && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
						}
						// meet bracket( "(", ")", "{")
						else if(bool_bracket && !bool_punctuation && bracket_form.contains(tkn)){
							
							if(tkn.equals("(")){

								bool_punctuation = false;
								bool_bracket = false;

							}else{

								bool_punctuation = false;
								bool_bracket = true;

							}
							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");

						}else{

							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {
								skipped_token += tokenBuf.get(i).get(j);
								j++;
							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
						}

						bool_elseif = true;
						bool_else = true;

        				break;
        				
    				// for 
        			case 6:
              
						// for (i = 1; i < 10; i++) {
						// meet identifier, operator, comparator, number
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = tokenBuf.get(i).get(j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}
							
							if(mat_identifier.matches()){
								identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "identifier");

							}else if(operator_form.contains(tkn)){

								if(tkn.equals("++") || tkn.equals("--")){
									bool_bracket = true;
									bool_punctuation = false;
								}else{
									bool_bracket = false;
									bool_punctuation = false;
								}
								operator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = true;
								bool_bracket = false;
								Judgement += op.Judgement_Process_Line(tkn, "number");

							}else{

								undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
							}
						}
						// meet punctuation
						else if(!bool_bracket && bool_punctuation && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");

						}
						// meet bracket( "(", ")", "{")
						else if(bool_bracket && !bool_punctuation && bracket_form.contains(tkn)){
							
							if(tkn.equals("(")){

								bool_punctuation = false;
								bool_bracket = false;

							}else{
 
								bool_punctuation = false;
								bool_bracket = true;

							}
							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");

						}else{

							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {
								skipped_token += tokenBuf.get(i).get(j);
								j++;
							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "skipped token");
						}

        				break;
        				
    				// while 
        			case 7:

						// while(i < 10) {
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = tokenBuf.get(i).get(j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){

								identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "identifier");

							}else if(operator_form.contains(tkn)){

								// if(tkn.equals("++") || tkn.equals("--")){
								// 	bool_bracket = true;
								// 	bool_punctuation = false;
								// }else{
								// 	bool_bracket = false;
								// 	bool_punctuation = false;
								// }
								operator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = false;
								bool_bracket = true;
								Judgement += op.Judgement_Process_Line(tkn, "number");

							}else{

								undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
							}
						}
						// meet punctuation
						else if(((!bool_bracket && bool_punctuation) || bool_doWhile) && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
						}
						// meet bracket( "(", ")", "{")
						else if(bool_bracket && !bool_punctuation && bracket_form.contains(tkn)){
							
							if(tkn.equals("(")){

								bool_punctuation = false;
								bool_bracket = false;

							}else if(tkn.equals(")")){

								bool_doWhile = true;
								bool_bracket = true;

							}else{

								bool_punctuation = false;
								bool_bracket = true;

							}
							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");

						}else{

							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {
								skipped_token += tokenBuf.get(i).get(j);
								j++;
							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");

						}

        				break;
        				
    				// do 
        			case 8:

						if(bool_bracket && bracket_form.contains(tkn)){

							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
							bool_doWhile = true;
							bool_bracket = false;
							bool_punctuation = true;

						}else{

							undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
						}

        				break;
        				
    				// return 
        			case 9:
        				mat_number = ptn_number.matcher(tkn);
						// meet number
						if(mat_number.matches()){
							number.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "number");
						}
						else if(identifier.token_defined(tkn)){
							identifier.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "identifier");
						}
						// meet punctuation ";"
						else if(tkn.equals(";")){
							punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
							bool_endLine = true;
						}
						// meet undefined
						else{
							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							state = 16;
						}
        				break;
        				
    				// switch 
        			case 10:
        				if(!bool_bracket && tkn.equals("(")) {
	    					bool_bracket = true;
		        			bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
        				}else if(bool_bracket){
        					if (compareIdentifier(tkn)) {
        						if(identifier.token_defined(tkn)) {
        							identifier.addMap(tkn);
        							Judgement += op.Judgement_Process_Line(tkn, "identifier");
        						}else {
        							undefinedToken.addMap(tkn);
        							Judgement += op.Judgement_Process_Line(tkn, "undefined");
            						state = 16;
        						}
        					}else if(tkn.equals(")")) {
    	    					bool_bracket = true;
    		        			bracket.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        		}else if(tkn.equals("{")) {
    	    					bool_bracket = false;
    		        			bracket.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        		}else {
    		        			undefinedToken.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "undefined");
        						state = 16;
    		        		}
        				}else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined");
    						state = 16;
		        		}
        				break;
        				
    				// case 
        			case 11:
        				if (tkn.matches("[0-9]")||tkn.equals("\'")) {
    						if(tkn.matches("[0-9]")) {
    							number.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "number");
    						}else if(tkn.matches("\'")) {
    							String s = tkn;
    							for(int k=1; k<3;k++) {
    								s+=tokenBuf.get(i).get(++j);
    							}
    							character.addMap(s);
    							Judgement += op.Judgement_Process_Line(s, "character");
    							
    						}else{
    							undefinedToken.addMap(tkn);
    							Judgement += op.Judgement_Process_Line(tkn, "undefined");
        						state = 16;
    						}
    					}else if(bool_case && tkn.equals(":")){
	    					bool_bracket = true;
		        			bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");

    	    	        	bool_case = false;
	    					bool_endLine = true;
        				}else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined");
    						state = 16;
		        		}
        				break;
        				
    				// printf 
        			case 12:
        				if(!bool_bracket && tkn.equals("(")) {
	    					bool_bracket = true;
		        			bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
	        			
        				} else if(bool_bracket){
        					Matcher mat_identifier = ptn_identifier.matcher(tkn);
        					if (bool_double) {
        						boolean undefined = false;
    		        			if(tokenBuf.get(i).get(j-2).equals(",")) {
    		        				identifier.addMap(tkn);
									Judgement += op.Judgement_Process_Line(tkn, "identifier");
    		        			}if (tkn.equals("%")) {
	    	        				String s = tokenBuf.get(i).get(j+1);
	    	        				char fst = s.charAt(0);
	    	        				String pointer_tmp = tkn + fst;
	    	        				String a = "";
	    	        				
	    	        				for(int k=0 ; k<s.length() ; k++) {
	    	        					if(k==0 && compareString("([cdf])", String.valueOf(fst)) ) {
	    	        						formatSpecifier.addMap(pointer_tmp);
											Judgement += op.Judgement_Process_Line(pointer_tmp, "Format_specifier");
	    	        					
	    	        					} else { 
	    	        						undefined = true;
	    	        						a +=s.charAt(k);
		        						}
	    	        				}
	        						j++;
	    	        				
	    	        				if(undefined) {
	    	        					printedToken.addMap(a);
										Judgement += op.Judgement_Process_Line(a, "printed_token");
	    	        				}
	    		        		}else if (tkn.equals("\\")) {
        		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
        		        				String pointer_tmp = "";
                						pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
                						formatSpecifier.addMap(pointer_tmp);
    									Judgement += op.Judgement_Process_Line(pointer_tmp, "Format_specifier");
                		        		j++;
                		        		
        		        			} else {
        			        			undefinedToken.addMap(tkn);
    									Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
        	    						state = 16;
        			        		}
        		        		}else if(tkn.equals("\"")) {
        		        			bool_double=false;
        		        			punctuation.addMap(tkn);
    								Judgement += op.Judgement_Process_Line(tkn, "punctuation");
        		        		}else if(mat_identifier.matches()){
        		        			printedToken.addMap(tkn);
									Judgement += op.Judgement_Process_Line(tkn, "printed_token");
        		        		} else {//判斷printtoken
        		        			String s="";
        		        			while(!tokenBuf.get(i).get(j).equals(" ")){
        		        				s+=tokenBuf.get(i).get(j);
        		        				j++;
        		        			}
    		        				printedToken.addMap(s);
									Judgement += op.Judgement_Process_Line(s, "printed_token");
    		        			}
    		        		} else if (tkn.equals(",") || tkn.equals("\"")) {
    		        			if(tkn.equals("\"")) {
    		        				bool_double=true;
    		        				punctuation.addMap(tkn);
    								Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    		        			}else {
    		        				punctuation.addMap(tkn);
    		        				Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    		        			}
        						
    		        		}else if(tkn.equals(")")) {
    	    					bool_bracket = false;
    		        			bracket.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        			
    		        		}else if(mat_identifier.matches() && identifier.get_TokenMap().containsKey(tkn)){
								formatSpecifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "identifier");
							}else {
    		        			undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
        						state = 16;
        						
    		        		}
        				} else if (tkn.equals(";")) {
		        			punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    						bool_endLine = true;
    						
		        		} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
    						state = 16;
    						
		        		}
        				break;
        				
    				// scanf
        			case 13:
        				if(!bool_bracket && tkn.equals("(")) {
        					bool_bracket = true;
		        			bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
		        			
        				} else if(bool_bracket){
        					Matcher mat_identifier = ptn_identifier.matcher(tkn);
        					if(bool_double) {
	        					boolean undefined = false;
	        					if (tkn.equals("%")) {
	    	        				String s = tokenBuf.get(i).get(j+1);
	    	        				char fst = s.charAt(0);
	    	        				String pointer_tmp = tkn + fst;
	    	        				String a = "";
	    	        				
	    	        				for(int k=0 ; k<s.length() ; k++) {
	    	        					if(k==0 && compareString("([cdf])", String.valueOf(fst)) ) {
	    	        						formatSpecifier.addMap(pointer_tmp);
											Judgement += op.Judgement_Process_Line(pointer_tmp, "Format_specifier");
	    	        					
	    	        					} else { 
	    	        						undefined = true;
	    	        						a +=s.charAt(k);
		        						}
	    	        				}
	        						j++;
	    	        				if(undefined) {
	    	        					printedToken.addMap(a);
										Judgement += op.Judgement_Process_Line(a, "printed_token");
		        						
	    	        				}
	    		        		}else if (tkn.equals("\\")) {
        		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
        		        				String pointer_tmp = "";
                						pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
                						formatSpecifier.addMap(pointer_tmp);
    									Judgement += op.Judgement_Process_Line(pointer_tmp, "Format_specifier");
                		        		j++;
                		        		
        		        			} else {
        			        			undefinedToken.addMap(tkn);
    									Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
        	    						state = 16;
        			        		}
        		        		}else if(tkn.equals("\"")) {
        		        			bool_double=false;
        		        			punctuation.addMap(tkn);
    								Judgement += op.Judgement_Process_Line(tkn, "punctuation");
        		        		} else if(mat_identifier.matches()){
        		        			printedToken.addMap(tkn);
									Judgement += op.Judgement_Process_Line(tkn, "printed_token");
        		        		} else {//判斷printtoken
        		        			String s="";
        		        			while(!tokenBuf.get(i).get(j).equals(" ")){
        		        				s+=tokenBuf.get(i).get(j);
        		        				j++;
        		        			}
    		        				printedToken.addMap(s);
									Judgement += op.Judgement_Process_Line(s, "printed_token");
    		        			}
        					}
        					else if(tkn.equals("&")) {
    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1)) && identifier.get_TokenMap().containsKey(tokenBuf.get(i).get(j+1))) {
//    		        			if(compareIdentifier(tokenBuf.get(i).get(j+1))) {
    		        				String pointer_tmp = "";
            						pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
            						address.addMap(pointer_tmp);
									Judgement += op.Judgement_Process_Line(pointer_tmp, "address");
            		        		j++;
            		        		
    		        			} else {
									String undefindString = "";
									undefindString = tkn + tokenBuf.get(i).get(++j);
    			        			undefinedToken.addMap(undefindString);
									Judgement += op.Judgement_Process_Line(undefindString, "undefined_token");
    	    						state = 16;
    			        		}
    		        		}else if (tkn.equals(",") || tkn.equals("\"")) {
    		        			if(tkn.equals("\"")) {
    		        				bool_double=true;
    		        				punctuation.addMap(tkn);
    								Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    		        			}else {
    		        				punctuation.addMap(tkn);
    		        				Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    		        			}
        						
    		        		}else if(tkn.equals(";")){
    		        			punctuation.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    		        			bool_endLine = true;
    		        			
    		        		}else if(tkn.equals(")")) {
    		        			bracket.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "bracket");
    		        			bool_bracket = false;
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
        						state = 16;
    		        		}
        					
        				} else if (tkn.equals(";")) {
		        			punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
    						bool_endLine = true;
		        		} else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
    						state = 16;
		        		}
        				break;
        			
        			// m = m + 100;
        			case 14:
						// k = k + i;
						// meet identifier
						if(identifier.token_defined(tkn)){
							identifier.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "identifier");
						}
						// meet character
						else if(tkn.equals("\'") && tokenBuf.get(i).get(j+2).equals("\'")){
							
							for(int s = j;s < j+3;s++){
								char_token += tokenBuf.get(i).get(s);
							}
							j+=2;

							character.addMap(char_token);
							Judgement += op.Judgement_Process_Line(char_token, "character");
							char_token = "";
							
						}
						// meet pointer
						else if(tkn.equals("*")){

							String pointer_tmp = "";

							// ia = 5 * ie; => "*" 視為operator, "ie" 視為 undefined token
							// c = a * 2; 
							if(tokenBuf.get(i).get(j+1).equals(" ")){
								mat_number = ptn_number.matcher(tokenBuf.get(i).get(j+2));
								operator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "operator");
								j+=2;
								// 若 * 後為數字
								if(mat_number.matches()){
									number.addMap(tokenBuf.get(i).get(j));
									Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(j), "number");
									break;
								}
								// 若 * 後為 idetifier
								else{
									// identifier 有宣告
									if(identifier.get_TokenMap().containsKey(tokenBuf.get(i).get(j))){
										identifier.addMap(tokenBuf.get(i).get(j));
										Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(j), "identifier");
										break;
									}
									// identifier 沒有宣告
									else{
										undefinedToken.addMap(tokenBuf.get(i).get(j));
										Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(j), "undefined_token");
										state = 16;
										break;
									}
								}
							}
							
							pointer_tmp = tkn + tokenBuf.get(i).get(j+1);
							// pointer 有先宣告
							if(pointer.get_TokenMap().containsKey(pointer_tmp)){
								
								pointer.addMap(pointer_tmp);
								Judgement += op.Judgement_Process_Line(pointer_tmp, "pointer");
								j++;
							}
							// pointer 沒有先宣告
							else{

								undefinedToken.addMap(pointer_tmp);
								Judgement += op.Judgement_Process_Line(pointer_tmp, "undefined_token");
								j++;
								state = 16;
							}
							
						}
						// meet operator
						else if(operator_form.contains(tkn)){
							operator.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "operator");
						}
						// meet punctuation ";"
						else if(tkn.equals(";")){
							punctuation.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");
							bool_endLine = true;
						}
						// meet bracket
						else if(bracket_form.contains(tkn)){
							// meet left bracket
							if(!bool_bracket){
								bracket.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "bracket");
								bool_bracket = true;
								bracket_check = bracket_form.indexOf(tkn);
							}
							// meet right bracket 
							else{
								// wrong right bracket
								if(!bracket_form.get(bracket_check+1).equals(tkn) && bool_bracket){
									undefinedToken.addMap(tkn);
									Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
									state = 16;
								}
								// correct right bracket
								else{
									bracket.addMap(tkn);
									Judgement += op.Judgement_Process_Line(tkn, "bracket");
									bool_bracket = false;
								}
							}
							
						}
						else{
							mat_number = ptn_number.matcher(tkn);
							
							// meet number
							if(mat_number.matches()){
								number.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "number");
							}
							// meet undefined
							else{
								undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								state = 16;
							}
						}
        				break;

        			// }
        			case 15:
        				if(tokenBuf.get(i).get(j).equals("/") || tokenBuf.get(i).get(j).equals("*")) {
	        				while(j < tokenBuf.get(i).size())
		        				sa += tokenBuf.get(i).get(j++);
		        			comment.addMap(sa);
							Judgement += op.Judgement_Process_Line(sa, "comment");
	        			} 
						else {
		        			undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
    						state = 16;
		        		}
        				break;


        			// skipped
        			case 16:
						String skip_tmp = ""; 
						while(j < tokenBuf.get(i).size()) {
							skip_tmp = skip_tmp + tokenBuf.get(i).get(j);
    						j++;
						}

						skippedToken.addMap(skip_tmp);
						Judgement += op.Judgement_Process_Line(skip_tmp, "skipped_token");
						bool_endLine = true;
						
        				break;
        			
					// elseif
					case 17:
						
						// meet identifier, operator, comparator, number
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = tokenBuf.get(i).get(j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){
								identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "identifier");

							}else if(operator_form.contains(tkn)){

								if(tkn.equals("++") || tkn.equals("--")){
									bool_bracket = true;
									bool_punctuation = false;
								}else{
									bool_bracket = false;
									bool_punctuation = false;
								}
								operator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = false;
								bool_bracket = true;
								Judgement += op.Judgement_Process_Line(tkn, "number");

							}else{

								undefinedToken.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
							}
						}
						// meet punctuation
						else if(!bool_bracket && bool_punctuation && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							Judgement += op.Judgement_Process_Line(tkn, "punctuation");

						}
						// meet bracket( "(", ")", "{")
						else if(bool_bracket && !bool_punctuation && bracket_form.contains(tkn)){
							
							if(tkn.equals("(")){

								bool_punctuation = false;
								bool_bracket = false;

							}else{
 
								bool_punctuation = false;
								bool_bracket = true;

							}
							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
						}
						else{

							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {
								skipped_token += tokenBuf.get(i).get(j);
								j++;
							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
						}

						break;
					// else
					case 18:
						if(bool_bracket && bracket_form.contains(tkn)){

							bracket.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "bracket");
							bool_bracket = true;
							bool_elseif = false;
							bool_else = false;
						}
						else{
							undefinedToken.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "undefined_token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {
								skipped_token += tokenBuf.get(i).get(j);
								j++;
							}
							skippedToken.addMap(skipped_token);
							Judgement += op.Judgement_Process_Line(skipped_token, "skipped_token");
						}
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

	public int total_token(){
		int total = 0;

		total = reservedWord.get_Amount() + libraryName.get_Amount() + identifier.get_Amount()
				+ character.get_Amount() + number.get_Amount() + pointer.get_Amount() 
				+ bracket.get_Amount() + operator.get_Amount() + comparator.get_Amount()
				+ address.get_Amount() + punctuation.get_Amount() + formatSpecifier.get_Amount()
				+ printedToken.get_Amount() + comment.get_Amount() + undefinedToken.get_Amount()
				+ skippedToken.get_Amount();
		
		return total;
	}
	

	public Map<String, Integer> get_ReserveWord_TokenMap(){
		return reservedWord.get_TokenMap();
	}
	public int get_ReserveWord_Amount(){
		return reservedWord.get_Amount();
	}
	public Map<String, Integer> get_LibraryName_TokenMap(){
		return libraryName.get_TokenMap();
	}
	public int get_LibraryName_Amount(){
		return libraryName.get_Amount();
	}
	public Map<String, Integer> get_Identifier_TokenMap(){
		return identifier.get_TokenMap();
	}
	public int get_Identifier_Amount(){
		return identifier.get_Amount();
	}
	public Map<String, Integer> get_Character_TokenMap(){
		return character.get_TokenMap();
	}
	public int get_Character_Amount(){
		return character.get_Amount();
	}
	public Map<String, Integer> get_Number_TokenMap(){
		return number.get_TokenMap();
	}
	public int get_Number_Amount(){
		return number.get_Amount();
	}
	public Map<String, Integer> get_Pointer_TokenMap(){
		return pointer.get_TokenMap();
	}
	public int get_Pointer_Amount(){
		return pointer.get_Amount();
	}
	public Map<String, Integer> get_Bracket_TokenMap(){
		return bracket.get_TokenMap();
	}
	public int get_Bracket_Amount(){
		return bracket.get_Amount();
	}
	public Map<String, Integer> get_Operator_TokenMap(){
		return operator.get_TokenMap();
	}
	public int get_Operator_Amount(){
		return operator.get_Amount();
	}
	public Map<String, Integer> get_Comparator_TokenMap(){
		return comparator.get_TokenMap();
	}
	public int get_Comparator_Amount(){
		return comparator.get_Amount();
	}
	public Map<String, Integer> get_Address_TokenMap(){
		return address.get_TokenMap();
	}
	public int get_Address_Amount(){
		return address.get_Amount();
	}
	public Map<String, Integer> get_Punctuation_TokenMap(){
		return punctuation.get_TokenMap();
	}
	public int get_Punctuation_Amount(){
		return punctuation.get_Amount();
	}
	public Map<String, Integer> get_Format_Specifier_TokenMap(){
		return formatSpecifier.get_TokenMap();
	}
	public int get_Format_Specifier_Amount(){
		return formatSpecifier.get_Amount();
	}
	public Map<String, Integer> get_PrintedToken_TokenMap(){
		return printedToken.get_TokenMap();
	}
	public int get_PrintedToken_Amount(){
		return printedToken.get_Amount();
	}
	public Map<String, Integer> get_Comment_TokenMap(){
		return comment.get_TokenMap();
	}
	public int get_Comment_Amount(){
		return comment.get_Amount();
	}
	public Map<String, Integer> get_UndefinedToken_TokenMap(){
		return undefinedToken.get_TokenMap();
	}
	public int get_UndefinedToken_Amount(){
		return undefinedToken.get_Amount();
	}
	public Map<String, Integer> get_Skipped_Token_TokenMap(){
		return skippedToken.get_TokenMap();
	}
	public int get_Skipped_Token_Amount(){
		return skippedToken.get_Amount();
	}

	// get all 判斷的過程
	public String get_Judgement(){
		return Judgement;
	}
}

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type

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

	private ArrayList<String> operator_form = new ArrayList<String>(){
		{
			add("+");add("-");add("*");add("/");add("++");add("--");
			add("%");add("^");add("&");add("|");add("=");
		}
	};
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
     
    Pattern ptn_else = Pattern.compile("else", Pattern.CASE_INSENSITIVE); 
    Pattern ptn_elseif = Pattern.compile("elseif", Pattern.CASE_INSENSITIVE); 
    
    Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)"); 
    Pattern ptn_identifier = Pattern.compile("([a-zA-Z])([a-zA-Z0-9]*)"); 

	Pattern ptn_number = Pattern.compile("-?\\d+");

	//Pattern ptn_operator = Pattern.compile("([*/%^[+{1,2}][-{1,2}]&|=])");
    
    public boolean compareString( String sA, String sB) {	// sA放正確的字串 sB放需要被比字串
    	Pattern ptn = Pattern.compile(sA, Pattern.CASE_INSENSITIVE); 
    	Matcher mat	= ptn.matcher(sB);
    	if(mat.matches())
    		return true;
    	else
    		return false;
    }
    
    public void scan() {
		int state = 0;
		String case_14_firstWord = null;

		boolean bool_endLine = true;
//		boolean bool_include = false;
		boolean bool_punctuation = false;
		boolean bool_bracket = false;
		boolean bool_doWhile = false;

		boolean bool_elseif = false;
		boolean bool_else = false;
		
		

    	for(int i=0 ; i<tokenBuf.size() ; i++) {
    		bool_endLine = true;
    		for(int j=0 ; j<tokenBuf.get(i).size() ; j++) {
    			String tkn = getOneTokenBuf(i, j);

				System.out.println("token now: "+tkn);
				
    			if(bool_endLine) {
	    			bool_endLine = false;

	        		if(compareString("#", tkn)) {
	        			state = 0;
        				punctuation.addMap(tkn);
						System.out.println("token "+tkn+" belongs to punctuation");
						
	        		}
	        		else if(compareString("main", tkn)) {
	        			state = 1;
	        			
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
	        			bool_bracket = true;
						reservedWord.addMap(tkn);
						System.out.println("token " + tkn + "belongs to reserved word");

	        		}
	        		else if(compareString("for", tkn))  {
	        			state = 6;
	        			
						bool_bracket = true;
						bool_punctuation = false;
						reservedWord.addMap(tkn);
						System.out.println("token " + tkn + " belongs to reserved word");
	        		}
	        		else if(compareString("while", tkn))  {
	        			state = 7;
	        			
						bool_bracket = true;
						bool_punctuation = false;
						reservedWord.addMap(tkn);
						System.out.println("token " + tkn + " belongs to reserved word");

	        		}
	        		else if(compareString("do", tkn))  {
	    				state = 8;
	    				
						bool_bracket = true;
						bool_punctuation = false;
						reservedWord.addMap(tkn);
						System.out.println("token " + tkn + " belongs to reserved word");

	        		}
	    			else if(compareString("return", tkn))  {

	    				state = 9;
	    			}
	    			else if(compareString("switch", tkn))  {

	    				state = 10;
	    			}
	    	        else if(compareString("case", tkn))  {

	    	        	state = 11;
	    	        }
	    	        else if(compareString("printf", tkn))   {

	    	        	state = 12;
	    	        }
	    	        else if(compareString("scanf", tkn))  {

	    	        	state = 13;
	    	        }
	    	        else {
						state = 14;
						case_14_firstWord = tkn;
						if(identifier.token_defined(case_14_firstWord)){
							identifier.addMap(case_14_firstWord);
							System.out.println("token " + case_14_firstWord + " belongs to identifier");
						}else if(bool_bracket && case_14_firstWord.equals("}")){
							bracket.addMap(case_14_firstWord);
							System.out.println("token " + case_14_firstWord + " belongs to bracket 1");
						}
					}

	        		continue;
	    		}
    			

    			switch(state) {
    			
    				// #
        			case 0:
						
        				// 判斷#後面是否為include
        				String include_tmp = getOneTokenBuf(i, 1);
        				if(compareString("include", tkn)) {

        					// #後面是include
    						reservedWord.addMap("include");
    						System.out.println("token include belongs to reserved word");

        					// 合併<xxx.h>
        					String library_tmp = "";
        					for(int k=2 ; k<=6 ; k++)
        						library_tmp = library_tmp + tokenBuf.get(i).get(k);
        					
        					// 判斷#include後面是否為<xxx.h>
        					Matcher mat_libname = ptn_libname.matcher(library_tmp);
        					if(mat_libname.matches()) {
        						// token格式為:<xxx.h>
        						libraryName.addMap(library_tmp);
        						System.out.println("token "+library_tmp+" belongs to library name");
        						
        						// 結束換下一行
        						bool_endLine = true;
								j = tokenBuf.get(i).size()-1;
        					} else {
        						// library格式不正確
        						comparator.addMap(tkn);
        						System.out.println("token "+tkn+" belongs to comparator");

        						// 取得並分類undefinedToken
        						String undefinedTokens = "";
        						int k = 3;
        						while(k < 6 && k < tokenBuf_space.get(i).size()) {
        							undefinedTokens = undefinedTokens + tokenBuf_space.get(i).get(k);
        							k++;
        						}
        						undefinedToken.addMap(undefinedTokens);
        						
        						// 取得並分類skipToken
        						String skipTokens = "";
        						System.out.println("token "+undefinedTokens+" belongs to undefined token");
        						while(k < tokenBuf_space.get(i).size()) {
            						skipTokens = skipTokens + tokenBuf_space.get(i).get(k);
        							k++;
        						}
        						skippedToken.addMap(skipTokens);
        						System.out.println("token "+skipTokens+" belongs to skipped token");

        						// 結束換下一行
        						bool_endLine = true;
        					}
        				} else {
        					undefinedToken.addMap(include_tmp);
    						String undefinedTokens = "";
    						String skipTokens = "";
							undefinedTokens = tokenBuf.get(i).get(2);
    						undefinedToken.addMap(undefinedTokens);
    						System.out.println("token "+undefinedTokens+" belongs to undefined token");
    						
    						int k = 3;
    						while(k < tokenBuf_space.get(i).size()) {
        						skipTokens = skipTokens + tokenBuf_space.get(i).get(k);
    							k++;
    						}
    						skippedToken.addMap(skipTokens);
    						System.out.println("token "+skipTokens+" belongs to skipped token");
    						
    						// 結束換下一行
    						bool_endLine = true;
        				}
        				break;
        				
    				// main
        			case 1:
        				break;
        				
        			// char
        			case 2:

		        		if (!bool_punctuation) {

    		        		Matcher mat_identifier = ptn_identifier.matcher(tkn);
    		        		if (mat_identifier.matches()) {
    		        			
    		        			identifier.addMap(tkn);
    		        			bool_punctuation = true;

								System.out.println("token " + tkn + " belongs to identifier");
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			
        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
    		        			if(mat_identifi.matches()) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + getOneTokenBuf(i, ++j);
            						pointer.addMap(pointer_tmp);

									System.out.println("token " + pointer_tmp + " belongs to pointer");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						while((j+1) < tokenBuf.get(i).size()) {
	        						tokens.addMap("skip token");
	        						j++;
        						}
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						while((j+1) < tokenBuf.get(i).size()) {
    							skippedToken.addMap("skip token");
        						j++;
    						}
		        		}
        				break;
        				
        			// int
        			case 3:
		        		if (!bool_punctuation) {

    		        		Matcher mat_identifier = ptn_identifier.matcher(tkn);
    		        		if (mat_identifier.matches()) {
    		        			
    		        			identifier.addMap(tkn);
    		        			bool_punctuation = true;

								System.out.println("token " + tkn + " belongs to identifier");
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			
        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
    		        			if(mat_identifi.matches()) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + getOneTokenBuf(i, ++j);
            						pointer.addMap(pointer_tmp);

									System.out.println("token " + pointer_tmp + " belongs to pointer");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						while((j+1) < tokenBuf.get(i).size()) {
	        						tokens.addMap("skip token");
	        						j++;
        						}
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						while((j+1) < tokenBuf.get(i).size()) {
    							skippedToken.addMap("skip token");
        						j++;
    						}
		        		}
        				break;
        				
    				// float
        			case 4:
		        		if (!bool_punctuation) {

    		        		Matcher mat_identifier = ptn_identifier.matcher(tkn);
    		        		if (mat_identifier.matches()) {
    		        			
    		        			identifier.addMap(tkn);
    		        			bool_punctuation = true;

								System.out.println("token " + tkn + " belongs to identifier");
    		        			
    		        		} else if (tkn.equals("*")) {
    		        			
        		        		Matcher mat_identifi = ptn_identifier.matcher(getOneTokenBuf(i, j+1));
    		        			if(mat_identifi.matches()) {
	    		        			String pointer_tmp = "";
            						pointer_tmp = tkn + getOneTokenBuf(i, ++j);
            						pointer.addMap(pointer_tmp);

									System.out.println("token " + pointer_tmp + " belongs to pointer");
	    		        			bool_punctuation = true;
    		        			}
    		        			
    		        		} else {
    		        			undefinedToken.addMap(tkn);
        						while((j+1) < tokenBuf.get(i).size()) {
	        						tokens.addMap("skip token");
	        						j++;
        						}
    		        		}
    		        		
		        		} else if(tkn.equals(",")){
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
		        			
		        		} else if(tkn.equals(";")) {
	        				bool_punctuation = false;
	        				punctuation.addMap(tkn);

							System.out.println("token " + tkn + " belongs to punctuation");
	        				bool_endLine = true;
	        				
		        		} else {
		        			undefinedToken.addMap(tkn);
    						while((j+1) < tokenBuf.get(i).size()) {
    							skippedToken.addMap("skip token");
        						j++;
    						}
		        		}
        				break;
        				
    				// if
        			case 5:
						//
						// meet identifier, operator, comparator, number
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = getOneTokenBuf(i, j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){
								identifier.addMap(tkn);
								System.out.println("token " + tkn + " belongs to identifier");

							}else if(operator_form.contains(tkn)){

								// if(tkn.equals("++") || tkn.equals("--")){
								// 	bool_bracket = true;
								// 	bool_punctuation = false;
								// }else{
								// 	bool_bracket = false;
								// 	bool_punctuation = false;
								// }
								operator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = false;
								bool_bracket = true;
								System.out.println("token " + tkn + " belongs to number");

							}else{

								undefinedToken.addMap(tkn);
								System.out.println("token " + tkn + " belongs to undefined token");
								System.out.println("token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {

									// skippedToken.addMap("skip token");
									// System.out.print(" " + tkn);
									skipped_token += tokenBuf.get(i).get(j);
									j++;

								}
								skippedToken.addMap(skipped_token);
								System.out.println("token " + skipped_token + " belongs to skipped token");

							}
						}
						// meet punctuation
						else if(!bool_bracket && bool_punctuation && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							System.out.println("token " + tkn + " belongs to punctuation");

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
							System.out.println("token " + tkn + " belongs to bracket");

						}else{

							undefinedToken.addMap(tkn);
							System.out.println("token " + tkn + " belongs to undefined token");
							System.out.println("token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {

								// skippedToken.addMap("skip token");
								// System.out.print(" " + tkn);
								skipped_token += tokenBuf.get(i).get(j);
								j++;

							}
							skippedToken.addMap(skipped_token);
							System.out.println("token " + skipped_token + " belongs to skipped token");
						}
        				break;
        				
    				// for 
        			case 6:
						// for (i = 1; i < 10; i++) {
						// meet identifier, operator, comparator, number
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = getOneTokenBuf(i, j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){
								identifier.addMap(tkn);
								System.out.println("token " + tkn + " belongs to identifier");

							}else if(operator_form.contains(tkn)){

								if(tkn.equals("++") || tkn.equals("--")){
									bool_bracket = true;
									bool_punctuation = false;
								}else{
									bool_bracket = false;
									bool_punctuation = false;
								}
								operator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = true;
								bool_bracket = false;
								System.out.println("token " + tkn + " belongs to number");

							}else{

								undefinedToken.addMap(tkn);
								System.out.println("token " + tkn + " belongs to undefined token");
								System.out.println("token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {

									// skippedToken.addMap("skip token");
									// System.out.print(" " + tkn);
									skipped_token += tokenBuf.get(i).get(j);
									j++;

								}
								skippedToken.addMap(skipped_token);
								System.out.println("token " + skipped_token + " belongs to skipped token");

							}
						}
						// meet punctuation
						else if(!bool_bracket && bool_punctuation && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							System.out.println("token " + tkn + " belongs to punctuation");

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
							System.out.println("token " + tkn + " belongs to bracket");

						}else{

							undefinedToken.addMap(tkn);
							System.out.println("token " + tkn + " belongs to undefined token");
							System.out.println("token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {

								// skippedToken.addMap("skip token");
								// System.out.print(" " + tkn);
								skipped_token += tokenBuf.get(i).get(j);
								j++;

							}
							skippedToken.addMap(skipped_token);
							System.out.println("token " + skipped_token + " belongs to skipped token");

						}
        				break;
        				
    				// while 
        			case 7:
						// while(i < 10) {
						if(!bool_bracket && !bool_punctuation){
							Matcher mat_identifier = ptn_identifier.matcher(tkn);
							
							// deal with ++, --, !=, ==, <=, >=
							String back_char = getOneTokenBuf(i, j+1);
							if((tkn.equals("+")||tkn.equals("-")||tkn.equals("=")||tkn.equals("!")||tkn.equals("<")||tkn.equals(">")) && (back_char.equals("=") || back_char.equals("+") || back_char.equals("-"))){
								tkn += back_char;
								j++;
							}

							if(mat_identifier.matches()){

								identifier.addMap(tkn);
								System.out.println("token " + tkn + " belongs to identifier");

							}else if(operator_form.contains(tkn)){

								// if(tkn.equals("++") || tkn.equals("--")){
								// 	bool_bracket = true;
								// 	bool_punctuation = false;
								// }else{
								// 	bool_bracket = false;
								// 	bool_punctuation = false;
								// }
								operator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to operator");

							}else if(comparator_form.contains(tkn)){
								
								comparator.addMap(tkn);
								System.out.println("token " + tkn + " belongs to comparator");

							}else if(ptn_number.matcher(tkn).matches()){

								number.addMap(tkn);
								bool_punctuation = false;
								bool_bracket = true;
								System.out.println("token " + tkn + " belongs to number");

							}else{

								undefinedToken.addMap(tkn);
								System.out.println("token " + tkn + " belongs to undefined token");
								System.out.println("token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {

									// skippedToken.addMap("skip token");
									// System.out.print(" " + tkn);
									skipped_token += tokenBuf.get(i).get(j);
									j++;

								}
								skippedToken.addMap(skipped_token);
								System.out.println("token " + skipped_token + " belongs to skipped token");

							}
						}
						// meet punctuation
						else if(((!bool_bracket && bool_punctuation) || bool_doWhile) && punctuation_form.contains(tkn)){

							punctuation.addMap(tkn);
							bool_punctuation = false;
							System.out.println("token " + tkn + " belongs to punctuation");

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
							System.out.println("token " + tkn + " belongs to bracket");

						}else{

							undefinedToken.addMap(tkn);
							System.out.println("token " + tkn + " belongs to undefined token");
							System.out.println("token");
							String skipped_token = null;
							while((j+1) < tokenBuf.get(i).size()) {

								// skippedToken.addMap("skip token");
								// System.out.print(" " + tkn);
								skipped_token += tokenBuf.get(i).get(j);
								j++;

							}
							skippedToken.addMap(skipped_token);
							System.out.println("token " + skipped_token + " belongs to skipped token");

						}
        				break;
        				
    				// do 
        			case 8:
						if(bool_bracket && bracket_form.contains(tkn)){

							bracket.addMap(tkn);
							System.out.println("token " + tkn + "belongs to bracket");
							bool_doWhile = true;
							bool_bracket = false;
							bool_punctuation = true;

						}else{

							undefinedToken.addMap(tkn);
								System.out.println("token " + tkn + " belongs to undefined token");
								System.out.println("token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									// skippedToken.addMap("skip token");
									// System.out.print(" " + tkn);
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								System.out.println("token " + skipped_token + " belongs to skipped token");

						}
        				break;
        				
    				// return 
        			case 9:
        				break;
        				
    				// switch 
        			case 10:
        				break;
        				
    				// case 
        			case 11:
        				break;
        				
    				// printf 
        			case 12:
        				break;
        				
    				// scanf
        			case 13:
        				break;
        				
        			default:
						// k = k + i;
						// meet identifier
						if(identifier.token_defined(tkn)){
							identifier.addMap(tkn);
							System.out.println("token " + tkn + " belongs to identifier");
						}
						// meet operator
						else if(operator_form.contains(tkn)){
							operator.addMap(tkn);
							System.out.println("token " + tkn + " belongs to operator");
						}
						// meet punctuation ";"
						else if(tkn.equals(";")){
							punctuation.addMap(tkn);
							System.out.println("token " + tkn + " belongs to punctuation");
						}
						
						else{
							Matcher mat_number = ptn_number.matcher(tkn);
							// meet number
							if(mat_number.matches()){
								number.addMap(tkn);
								System.out.println("token " + tkn + " belongs to number");
							}
							// meet undefined
							else{
								undefinedToken.addMap(tkn);
								System.out.println("token " + tkn + " belongs to undefined token");
								System.out.println("token");
								String skipped_token = null;
								while((j+1) < tokenBuf.get(i).size()) {
									// skippedToken.addMap("skip token");
									// System.out.print(" " + tkn);
									skipped_token += tokenBuf.get(i).get(j);
									j++;
								}
								skippedToken.addMap(skipped_token);
								System.out.println("token " + skipped_token + " belongs to skipped token");
							}
						}
    			}
    		}
    	}
    }
}

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
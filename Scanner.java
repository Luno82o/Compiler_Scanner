import java.io.*;
import java.util.*;
import java.util.regex.*; 

public class Scanner {
	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	
	static Output op = new Output();
    
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
	
    public void scan() {
    	String tkn = "";
    	
        Pattern ptn_libname = Pattern.compile("(<)([a-zA-Z]+)(.h>)"); 
        
    	for(int i=0 ; i<tokenBuf.size() ; i++) {
    		for(int j=0 ; j<tokenBuf.get(i).size() ; j++) {
    			tkn = tokenBuf.get(i).get(j);
    			
    			System.out.print(tkn + " ");

    			if(compareString("#", tkn)) {

    				punctuation.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "Punctuation");
					

    				// 判斷#後面是否為include
    				if(compareString("include", tokenBuf.get(i).get(j+1))) {
    					
    					j++;
    					// #後面是include
						reservedWord.addMap(tokenBuf.get(i).get(j));
						Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(j), "ReservedWord");

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
//    						comparator.addMap(tkn);
//							Judgement += op.Judgement_Process_Line(tkn, "Comparator");

    						// 取得並分類undefinedToken
    						String undefinedTokens = "";
    						int f = 3;
    						while(f < 6 && f < tokenBuf.get(i).size()) {
    							if(tokenBuf.get(i).get(f).equals(" ")) break;
    							undefinedTokens = undefinedTokens + tokenBuf.get(i).get(f);
    							f++;
    						}

        					SetUndefToken(i, j);
        					j = tokenBuf.get(i).size();
    					
    					}
    				} else {
    					SetUndefToken(i, j);
    					j = tokenBuf.get(i).size();
    				}
    			}
        		else if(compareString("main", tkn)) {
        			reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("char", tkn)) {
        			reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("int", tkn)) {
        			reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("float", tkn)) {
        			reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("if", tkn)) {
					reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("for", tkn))  {
        			reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("while", tkn))  {
					reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
        		else if(compareString("do", tkn))  {
					reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
        		}
    			else if(compareString("return", tkn))  {
					reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
    			}
    			else if(compareString("switch", tkn)) {
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
    			}
    	        else if(compareString("case", tkn))  {
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
    	        }
    	        else if(compareString("printf", tkn))   {
    	        	reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
    	        }
    	        else if(compareString("scanf", tkn)) {
    	        	reservedWord.addMap(tkn);
					Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
    	        }
    		}
    		System.out.println();
    	}
    }
    
    public void SetUndefToken(int i, int j) {

		undefinedToken.addMap(tokenBuf.get(i).get(j));
		Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(j), "Undefined_token");
		
		String skip_tmp = ""; 
		while(j < tokenBuf.get(i).size()) {
			skip_tmp = skip_tmp + tokenBuf.get(i).get(j);
			j++;
		}

		skippedToken.addMap(skip_tmp);
		Judgement += op.Judgement_Process_Line(skip_tmp, "skipped_token");
	
		
    }
    
    public boolean compareString(String sA, String sB) {	// sA放正確的字串 sB放需要被比字串
    	Pattern ptn = Pattern.compile(sA, Pattern.CASE_INSENSITIVE); 
    	Matcher mat	= ptn.matcher(sB);
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

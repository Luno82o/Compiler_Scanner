import java.io.*;
import java.util.*;

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

	
	
	int bracket_check = 0;
    
    
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



    

    public void scan() {
		
		// boolean bool_endLine = true;
		
		boolean bool_identifier_defined = false;
		// boolean bool_elseif = false;
		// boolean bool_else = false;

		
		
		
    	for(int i=0 ; i<tokenBuf.size() ; i++) {
    		// bool_endLine = true;
    		bool_identifier_defined = false;
			System.out.println(tokenBuf.get(i));

    		for(int j=0 ; j<tokenBuf.get(i).size() ; j++) {
    			String tkn = tokenBuf.get(i).get(j);
    			if(tkn.equals(" ")) continue;
    			
    			// if(bool_endLine) {
	    			// bool_endLine = false;
	    			
	        		if(tkn.matches("([a-zA-Z]*)")) {
						if(tkn.matches("([Ii][Nn][Cc][Ll][Uu][Dd][Ee])")){
							// #include <stdio.h>處理 
							reservedWord.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
							j = tokenBuf.get(i).size()-1;
						}
						else if(tkn.matches("([Mm][Aa][Ii][Nn])") || tkn.matches("([Ii][Ff])") || tkn.matches("([Ee][Ll][Ss][Ee])") 
							|| tkn.matches("([Ee][Ll][Ss][Ee][Ii][Ff])") || tkn.matches("([Ff][Oo][Rr])") || tkn.matches("([Dd][Oo])")
							|| tkn.matches("([Ww][Hh][Ii][Ll][Ee])") || tkn.matches("([Rr][Ee][Tt][Uu][Rr][Nn])") || tkn.matches("([Ss][Ww][Ii][Tt][Cc][Hh])")
							|| tkn.matches("([Cc][Aa][Ss][Ee])") || tkn.matches("([Pp][Rr][Ii][Nn][Tt][Ff])") || tkn.matches("([Ss][Cc][Aa][Nn][Ff])"))
						{
							
							reservedWord.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
						}
						else if(tkn.matches("([Cc][Hh][Aa][Rr])")|| tkn.matches("([Ii][Nn][Tt])") || tkn.matches("([Ff][Ll][Oo][Aa][Tt])")){
							
							
							bool_identifier_defined = true;
							reservedWord.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "ReservedWord");
						}
						else {
							// 1. identifier
							if(bool_identifier_defined || identifier.token_defined(tkn)){
								identifier.addMap(tkn);
								Judgement += op.Judgement_Process_Line(tkn, "Identifier");
							}
							// 2. undefined token
							else{
								Judgement += op.Judgement_Process_Line(tkn, "Undefined_token");
								j = skip_token(i, j);
							}
							
						}
						
	        		}
					// bracket
	        		else if(tkn.matches("^(\\[|\\]|\\(|\\)|\\{|\\})$"))  {
	        			
	        			bracket.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Bracket");
	        			
	        		}
					// character
					// elseif(i > 3){
	        		else if(peek(i, j, 3).matches("^(\\'[a-zA-Z]\\')$")) {
	        			
						tkn = tkn+tokenBuf.get(i).get(j+1)+tokenBuf.get(i).get(j+2);
						// System.out.println(tkn);
	        			j += 2;
						character.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Character");
						
	        		}
					
					// number
	        		else if(tkn.matches("^(\\(?-?\\d+)(\\.\\d+)?\\)?$")) {
						// 負數無括號
	        			if(tokenBuf.get(i).get(j-1).equals("-")){
							tkn = tokenBuf.get(i).get(j-1) + tkn;
						}
						// 負數有括號
						else if(tokenBuf.get(i).get(j-2).equals("(") && tokenBuf.get(i).get(j-1).equals("-")){
							String negative_int = "";
							for(int s = j-2;s < j+2;++s){
								negative_int += tokenBuf.get(i).get(s);
							}
							tkn = negative_int;
							j++;
						}
	        			number.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Number");
	        		}
					// pointer
	        		else if(pointer.get_TokenMap().containsKey(peek(i, j, 2)) || (bool_identifier_defined && peek(i, j, 2).matches("^(\\*[a-zA-Z]+)$"))) {
	        			tkn = peek(i, j, 2);
	        			pointer.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Pointer");
						j++;
	        		}
					// // address
	        		// else if(identifier.get_TokenMap().containsKey() && tokenBuf.get(i).get(j-1).equals("&")) {
	        		// 	tkn = tokenBuf.get(i).get(j-1) + tkn;
	        		// 	address.addMap(tkn);
					// 	Judgement += op.Judgement_Process_Line(tkn, "Address");
	        		// }
					// Comment
	        		else if(peek(i, j, 2).matches("^(\\/\\/|\\/\\*)$")) {

						for(int s = j+1;s < tokenBuf.get(i).size();s++){
							tkn += tokenBuf.get(i).get(s);
							if(s == tokenBuf.get(i).size()-1){
								j = s;
							}
						}
						comment.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Comment");

	        		}
					
					// format specidfier
					else if(peek(i, j, 2).matches("^(%d|%c|%f|\\[a-z])$")){
						++j;
						formatSpecifier.addMap(tkn+tokenBuf.get(i).get(j));
						Judgement += op.Judgement_Process_Line(tkn+tokenBuf.get(i).get(j), "Format_Specifier");
					}
					// operator or address
	        		else if(tkn.matches("^(\\+|-|\\*|\\|\\/|%|^|&|\\||=$)") || peek(i, j, 2).matches("^(\\+\\+|--)$"))  {
	        			if(identifier.token_defined(tokenBuf.get(i).get(j+1))){
							tkn += tokenBuf.get(i).get(++j);
							address.addMap(tkn);
							Judgement += op.Judgement_Process_Line(tkn, "Address");
							continue;
						}

						if(tokenBuf.get(i).get(j+1).matches("^(\\+|-)$")){
							tkn += tokenBuf.get(i).get(++j);
						}
						operator.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Operator");

	        		}
					// comparator
	        		else if(peek(i, j, 2).matches("^(==|!=|<=|>=)$") || tkn.matches("^(<|>)$")) {

						if((tkn+tokenBuf.get(i).get(j+1)).matches("^(==|!=|<=|>=)$")){
							tkn += tokenBuf.get(i).get(++j);
						}
	    				
						comparator.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Comparator");

	        		}
					// punctuation
					else if(tkn.matches("^(,|;|:|#|\"|')$")){
						punctuation.addMap(tkn);
						Judgement += op.Judgement_Process_Line(tkn, "Punctuation");
					}
					// printed token
	    			else if(tkn.equals("\"")){
						int s = j+1;
						boolean stop = !(tokenBuf.get(i).get(s).matches("^(\\|%|\")$"));
						for(;!stop;++s){
							printedToken.addMap(tokenBuf.get(i).get(s));
							Judgement += op.Judgement_Process_Line(tokenBuf.get(i).get(s), "Printed_Token");
						}
					}
					
					
	        		continue;
				
			}
    	}
	}

    
	public int skip_token(int line, int charAt){
		String skipped_token = "";
		for(int s = charAt+1;s < tokenBuf.get(line).size();++s){
			skipped_token += tokenBuf.get(line).get(s);
		}
		skippedToken.addMap(skipped_token);
		Judgement += op.Judgement_Process_Line(skipped_token, "Skipped_token");
		charAt = tokenBuf.get(line).size() - 1;
		return charAt;
	}

	public String peek(int line, int charAt, int peek_num){
		int bound = tokenBuf.get(line).size();
		String result = "";
		for(int s = charAt;s < charAt+peek_num && s < bound;s++){
			result += tokenBuf.get(line).get(s);
		}
		return result;
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


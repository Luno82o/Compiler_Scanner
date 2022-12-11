import java.io.*;
import java.util.*;

public class Scanner {
    Token tokens = new Token();
	
    public void scanner(String inputLine) {
        tokens.setTokenClass();
        
        String[] tokens_1st = splitSentence(inputLine);
        System.out.println(Arrays.toString(tokens_1st));

        int tokentype;
        
        for(int i = 0; i < tokens_1st.length; i++) {
        	if(i == 0 && tokens.getTokenClass(tokens_1st[i]) == 0) {
                System.out.println("token "+tokens_1st[i]+" belongs to reserved word.");
            	tokentype = tokens.getRWIndex(tokens_1st[i]);
                System.out.println("token type : "+tokentype);
            }else {
            	System.out.println("no");
            }
	    }
    }
    
    public String[] splitSentence(String line) {
    	/* 將code用空格分開
    	 * Ex:	int i, k, m, *id; ===> [int, i,, k,, m,, *id;]
    	 */
    	
    	StringTokenizer st = new StringTokenizer(line);
    	int count = st.countTokens();
    	String[] token_st = new String[count];
    	
    	for(int i=0; i<count; i++) {
    		token_st[i] = st.nextToken();
//			System.out.println(Arrays.toString(token_st));
    	}    	
    	return token_st;
    }
}

//System.out.println(valueName.getClass().getSimpleName());		//取得變數的type
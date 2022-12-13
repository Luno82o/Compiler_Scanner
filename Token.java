import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Token{
//	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	
	private static Map<String, Integer> tokens = new HashMap<>();
	private static int amount = 0;


    
    // 新增值到Map-tokens
    public void addMap(String token) {
    	if(tokens.containsKey(token)) {
    		int timeTmp = tokens.get(token);
    		timeTmp++;
    		tokens.replace(token, timeTmp);
    	} else {
    		tokens.put(token, 1);
    	}

    	System.out.println("addRWMap: "+token);
    }
    
    // 印出Map-reservedWord
    public void pMap() {
    	System.out.println(tokens);
    }
} 



////private static ArrayList<String> tokenClasses = new ArrayList<String>();
//private static ArrayList<String> reservedWord = new ArrayList<String>();
////private static ArrayList<String> libraryName = new ArrayList<String>();
////private static ArrayList<String> identifier = new ArrayList<String>();
////private static ArrayList<String> character = new ArrayList<String>();
////private static ArrayList<String> number = new ArrayList<String>();
////private static ArrayList<String> pointer = new ArrayList<String>();
////private static ArrayList<String> bracket = new ArrayList<String>();
////private static ArrayList<String> operator = new ArrayList<String>();
////private static ArrayList<String> comparator = new ArrayList<String>();
////private static ArrayList<String> address = new ArrayList<String>();
////private static ArrayList<String> punctuation = new ArrayList<String>();
////private static ArrayList<String> formatSpecifier = new ArrayList<String>();
////private static ArrayList<String> printedToken = new ArrayList<String>();
////private static ArrayList<String> comment = new ArrayList<String>();
////private static ArrayList<String> undefinedToken = new ArrayList<String>();
////private static ArrayList<String> SkippedToken = new ArrayList<String>();


//public class Token{
//	private static String a ;
//	private static Map b;
//	private static int c;
//	
//	public void addToken() {
//		b.add();
//	}
//}
//
//Token ReservedWord = new Token();
//Token LibraryName = new Token();
//Token Identifier = new Token();
//
//ReservedWord.addToken();
//LibraryName.addToken();
//Identifier

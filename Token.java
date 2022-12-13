import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Token{
	private static ArrayList<ArrayList<String>> tokenBuf = new ArrayList<>(2);
	
	private static ArrayList<String> tokenClasses = new ArrayList<String>();
//	private static ArrayList<String> reservedWord = new ArrayList<String>();
	private static Map<String, Integer> reservedWord = new HashMap<>();
	
    public void setTokenClass() {
    	tokenClasses.add("ReservedWord");
    	tokenClasses.add("LibraryName");
    	tokenClasses.add("Identifier");
    	tokenClasses.add("Character");
    	tokenClasses.add("Number");
    	tokenClasses.add("Pointer");
    	tokenClasses.add("Bracket");
    	tokenClasses.add("Operator");
    	tokenClasses.add("Comparator");
    	tokenClasses.add("Address");
    	tokenClasses.add("Punctuation");
    	tokenClasses.add("FormatSpecifier");
    	tokenClasses.add("PrintedToken");
    	tokenClasses.add("Comment");
    	tokenClasses.add("UndefinedToken");
    	tokenClasses.add("SkippedToken");
    	
//    	reservedWord.add("include");
//    	reservedWord.add("main");
//    	reservedWord.add("char");
//    	reservedWord.add("int");
//    	reservedWord.add("float");
//    	reservedWord.add("if");
//    	reservedWord.add("else");
//    	reservedWord.add("elseif");
//    	reservedWord.add("for");
//    	reservedWord.add("while");
//    	reservedWord.add("do");
//    	reservedWord.add("return");
//    	reservedWord.add("switch");
//    	reservedWord.add("case");
//    	reservedWord.add("printf");
//    	reservedWord.add("scanf");
    }
    
    // 新增值到ArrayList-tokenBuf
    public void addTokenBuf(ArrayList<String> token) {
    	tokenBuf.add(token);
    }

    // 印出ArrayList-tokenBuf
    public void coutTokenBuf() {
    	System.out.println(tokenBuf);
    }

    // 取得ArrayList-tokenBuf的第(x, y)位
    public String getTokenBuf(int x, int y) {
    	return String.valueOf(tokenBuf.get(x).get(y));
    }
    
    // 新增值到Map-reservedWord
    public void addRWMap(String token) {
    	if(reservedWord.containsKey(token)) {
    		int timeTmp = reservedWord.get(token);
    		timeTmp++;
    		reservedWord.replace(token, timeTmp);
    	} else {
    		reservedWord.put(token, 1);
    	}
    }
    
    // 印出Map-reservedWord
    public void pRWMap() {
    	System.out.println(reservedWord);
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

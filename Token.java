import java.util.ArrayList;

public class Token{
	private static ArrayList<ArrayList> tokenBuf = new ArrayList<ArrayList>();
	
	private static ArrayList<String> tokenClasses = new ArrayList<String>();
	private static ArrayList<String> reservedWord = new ArrayList<String>();
	
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
    	
    	reservedWord.add("include");
    	reservedWord.add("main");
    	reservedWord.add("char");
    	reservedWord.add("int");
    	reservedWord.add("float");
    	reservedWord.add("if");
    	reservedWord.add("else");
    	reservedWord.add("elseif");
    	reservedWord.add("for");
    	reservedWord.add("while");
    	reservedWord.add("do");
    	reservedWord.add("return");
    	reservedWord.add("switch");
    	reservedWord.add("case");
    	reservedWord.add("printf");
    	reservedWord.add("scanf");
    }
    
    public void addTokenBuf(ArrayList<String> token) {
    	tokenBuf.add(token);
    }
    
    public void coutTokenBuf() {
    	System.out.println(tokenBuf);
    }
    
    public String getTokenBuf(int x, int y) {
    	return String.valueOf(tokenBuf.get(x).get(y));
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

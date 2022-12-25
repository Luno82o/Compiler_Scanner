import java.util.Map;
//import java.util.ArrayList;
import java.util.HashMap;

public class Token{
	
	private Map<String, Integer> tokens = new HashMap<>();
	private int amount = 0;
	//private ArrayList<String> form = new ArrayList<>();

	public Token(){}

	// public Token(String line){

	// }

    // 新增值到Map-tokens
    public void addMap(String token) {
    	if(tokens.containsKey(token)) {
    		int timeTmp = tokens.get(token);
    		timeTmp++;
    		tokens.replace(token, timeTmp);
    	} else {
    		tokens.put(token, 1);
    	}

    	System.out.println("addMap: "+token);
    	addAmount();
    }
    
    // 印出Map-tokens
    public void pMap() {
    	System.out.println(tokens);
    }
    
	// 印出Map-tokens
    public void addAmount() {
    	amount++;
    }

	public boolean token_defined(String t){
		if(tokens.containsKey(t)){
			return true;
		}
		return false;
	}
} 

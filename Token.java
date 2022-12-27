import java.util.Map;
//import java.util.ArrayList;
import java.util.HashMap;

public class Token{
	private Map<String, Integer> tokens = new HashMap<>();
	private int amount = 0;

	public Token(){}

    // 新增值到Map-tokens
    public void addMap(String token) {
    	if(tokens.containsKey(token)) {
    		int timeTmp = tokens.get(token);
    		timeTmp++;
    		tokens.replace(token, timeTmp);
    	} else {
    		tokens.put(token, 1);
    	}

//    	System.out.println("addMap: "+token);
    	addAmount();
    }
    
    // 印出Map-tokens
    public void pMap() {
    	System.out.println(tokens);
    }
    
	// 增加數量
    public void addAmount() {
    	amount++;
    }
	// 確認token是否已被宣告
	public boolean token_defined(String t){
		if(tokens.containsKey(t)){
			return true;
		}
		return false;
	}

	// get amount
	public int get_Amount(){

		return this.amount;
		
	}

	// get Map<String, Integer>
	public Map<String, Integer> get_TokenMap(){
		
		return this.tokens;
	}

} 

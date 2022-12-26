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
	// return 該種 Token 的 total 
	public int tokenType_num(){
		for(Map.Entry<String, Integer> m : tokens.entrySet()){
			amount += m.getValue();
		}
		return amount;
	}

	// get Map<String, Integer>
	public Map<String, Integer> get_TokenMap(Map<String, Integer> tokens){
		return this.tokens;
	}
} 

import java.util.Map;
//import java.util.ArrayList;
import java.util.HashMap;

public class Token{
	private enum TokenType {ReservedWord, Library_name, Identifier, Character, Number, Pointer, Bracket, Operator, Comparator, Address, Punctuation, Format_specifier, Printed_token, Comment, Undefined_token, Skipped_token}

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
	// public int tokenType_num(){
		
	// 	for(Map.Entry<String, Integer> m : tokens.entrySet()){
	// 		amount += m.getValue();
	// 	}
	// 	return amount;
	// }

	// get amount
	public int get_Amount(){

		return this.amount;
		
	}

	// get Map<String, Integer>
	public Map<String, Integer> get_TokenMap(){
		
		return this.tokens;
	}

	// show judgement process
	public String Judgement_Process_Line(String token, String Type){
		String judge = "token " + token + " belongs to ";
		if(TokenType.ReservedWord.toString().equalsIgnoreCase(Type)){
			judge += ("reserved word" + "\n");
		}
		else if(TokenType.Library_name.toString().equalsIgnoreCase(Type)){
			judge += ("library name" + "\n");
		}
		else if(TokenType.Identifier.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Character.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Number.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Pointer.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Bracket.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Operator.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Comparator.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Address.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Punctuation.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Format_specifier.toString().equalsIgnoreCase(Type)){
			judge += ("format specifier" + "\n");
		}
		else if(TokenType.Printed_token.toString().equalsIgnoreCase(Type)){
			judge += ("printed token" + "\n");
		}
		else if(TokenType.Comment.toString().equalsIgnoreCase(Type)){
			judge += (Type.toLowerCase() + "\n");
		}
		else if(TokenType.Undefined_token.toString().equalsIgnoreCase(Type)){
			judge += ("undefined token" + "\n");
		}
		else if(TokenType.Skipped_token.toString().equalsIgnoreCase(Type)){
			judge += ("skipped token" + "\n");
		}
		else{
			judge += "Error\n";
		}
		return judge;
	}
} 

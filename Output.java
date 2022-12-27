import java.io.FileWriter;
import java.util.Map;

public class Output {
    
    public Output(){}

    int lineC = 1;

	private enum TokenType {ReservedWord, Library_name, Identifier, Character, Number, Pointer, Bracket, Operator, Comparator, Address, Punctuation, Format_specifier, Printed_token, Comment, Undefined_token, Skipped_token}

	// show judgement process
	public String Judgement_Process_Line(String token, String Type){
		String judge = (lineC++) + ". token " + token + " belongs to ";
		
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
	
    public void WriteFile(){
        
        Scanner sc = new Scanner();
        
        try {
            FileWriter writer = new FileWriter("output.txt");
            
            System.out.println(sc.get_Judgement());
            writer.write(sc.get_Judgement() + "\n\n\n");

            
            writer.write("Total: " + sc.total_token() + " tokens\n");

            if(sc.get_ReserveWord_TokenMap().size() > 0){
                writer.write("\nReserved word: " + sc.get_ReserveWord_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_ReserveWord_TokenMap()));
            }
            if(sc.get_LibraryName_TokenMap().size() > 0){
                writer.write("\nLibrary name: " + sc.get_LibraryName_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_LibraryName_TokenMap()));
            }
            if(sc.get_Identifier_TokenMap().size() > 0){
                writer.write("\nIdentifier: " + sc.get_Identifier_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Identifier_TokenMap()));
            }
            if(sc.get_Character_TokenMap().size() > 0){
                writer.write("\nCharacter: " + sc.get_Character_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Character_TokenMap()));
            }
            if(sc.get_Number_TokenMap().size() > 0){
                writer.write("\nNumber: " + sc.get_Number_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Number_TokenMap()));
            }
            if(sc.get_Pointer_TokenMap().size() > 0){
                writer.write("\nPointer: " + sc.get_Pointer_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Pointer_TokenMap()));
            }
            if(sc.get_Bracket_TokenMap().size() > 0){
                writer.write("\nBracket: " + sc.get_Bracket_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Bracket_TokenMap()));
            }
            if(sc.get_Operator_TokenMap().size() > 0){
                writer.write("\nOperator: " + sc.get_Operator_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Operator_TokenMap()));
            }
            if(sc.get_Comparator_TokenMap().size() > 0){
                writer.write("\nComparator: " + sc.get_Comparator_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Comparator_TokenMap()));
            }
            if(sc.get_Address_TokenMap().size() > 0){
                writer.write("\nAddress: " + sc.get_Address_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Address_TokenMap()));
            }
            if(sc.get_Punctuation_TokenMap().size() > 0){
                writer.write("\nPunctuation: " + sc.get_Punctuation_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Punctuation_TokenMap()));
            }
            if(sc.get_Format_Specifier_TokenMap().size() > 0){
                writer.write("\nFormat specifier: " + sc.get_Format_Specifier_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Format_Specifier_TokenMap()));
            }
            if(sc.get_PrintedToken_TokenMap().size() > 0){
                writer.write("\nPrinted token: " + sc.get_PrintedToken_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_PrintedToken_TokenMap()));
            }
            if(sc.get_Comment_TokenMap().size() > 0){
                writer.write("\nComment: " + sc.get_Comment_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Comment_TokenMap()));
            }
            if(sc.get_UndefinedToken_TokenMap().size() > 0){
                writer.write("\nUndefined token: " + sc.get_UndefinedToken_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_UndefinedToken_TokenMap()));
            }
            if(sc.get_Skipped_Token_TokenMap().size() > 0){
                writer.write("\nSkipped token: " + sc.get_Skipped_Token_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Skipped_Token_TokenMap()));
            }
 
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Traverse_Token_Map(Map<String, Integer> token){
        
        String output = "";
        for(Map.Entry<String, Integer> m : token.entrySet()){
            if(m.getValue() > 1){
                output += (m.getKey() + " (x" + m.getValue() + ")\n");
            }else{
                output += (m.getKey() + "\n");
            }
        }
        return output;
    }
    
}

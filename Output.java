import java.io.FileWriter;
import java.util.Map;

public class Output {
    
    public Output(){}
    
    // public void File_Write_TotalToken(){
    //     Scanner scanner = new Scanner();

    //     try {

    //         FileWriter writer = new FileWriter("output.txt");
    //         writer.write("Total: " + scanner.total_token() + "tokens\n");
    //         writer.close();

    //     } catch (Exception e) {
    //         // TODO: handle exception

    //     }
        
    // }

    public void WriteFile(){
        
        Scanner sc = new Scanner();
        try {
            
            FileWriter writer = new FileWriter("output.txt");

            writer.write("Total: " + sc.total_token() + "tokens\n");

            if(sc.get_ReserveWord().get_TokenMap().size() > 0){
                writer.write("\nReserved word: " + sc.get_ReserveWord().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_ReserveWord()));
            }
            if(sc.get_LibraryName().get_TokenMap().size() > 0){
                writer.write("\nLibrary name: " + sc.get_LibraryName().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_LibraryName()));
            }
            if(sc.get_Identifier().get_TokenMap().size() > 0){
                writer.write("\nIdentifier: " + sc.get_Identifier().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Identifier()));
            }
            if(sc.get_Character().get_TokenMap().size() > 0){
                writer.write("\nCharacter: " + sc.get_Character().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Character()));
            }
            if(sc.get_Number().get_TokenMap().size() > 0){
                writer.write("\nNumber: " + sc.get_Number().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Number()));
            }
            if(sc.get_Pointer().get_TokenMap().size() > 0){
                writer.write("\nPointer: " + sc.get_Pointer().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Pointer()));
            }
            if(sc.get_Bracket().get_TokenMap().size() > 0){
                writer.write("\nBracket: " + sc.get_Bracket().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Bracket()));
            }
            if(sc.get_Operator().get_TokenMap().size() > 0){
                writer.write("\nOperator: " + sc.get_Operator().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Operator()));
            }
            if(sc.get_Comparator().get_TokenMap().size() > 0){
                writer.write("\nComparator: " + sc.get_Comparator().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Comparator()));
            }
            if(sc.get_Address().get_TokenMap().size() > 0){
                writer.write("\nAddress: " + sc.get_Address().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Address()));
            }
            if(sc.get_Punctuation().get_TokenMap().size() > 0){
                writer.write("\nPunctuation: " + sc.get_Punctuation().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Punctuation()));
            }
            if(sc.get_Format_Specifier().get_TokenMap().size() > 0){
                writer.write("\nFormat specifier: " + sc.get_Format_Specifier().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Format_Specifier()));
            }
            if(sc.get_PrintedToken().get_TokenMap().size() > 0){
                writer.write("\nPrinted token: " + sc.get_PrintedToken().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_PrintedToken()));
            }
            if(sc.get_Comment().get_TokenMap().size() > 0){
                writer.write("\nComment: " + sc.get_Comment().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Comment()));
            }
            if(sc.get_UndefinedToken().get_TokenMap().size() > 0){
                writer.write("\nUndefined token: " + sc.get_UndefinedToken().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_UndefinedToken()));
            }
            if(sc.get_Skipped_Token().get_TokenMap().size() > 0){
                writer.write("\nSkipped token: " + sc.get_Skipped_Token().get_Amount() + "\n");
                writer.write(Traverse_Token_Map(sc.get_Skipped_Token()));
            }
 
            writer.close();
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String Traverse_Token_Map(Token token){

        String output = "";

        for(Map.Entry<String, Integer> m : token.get_TokenMap().entrySet()){
            if(m.getValue() > 1){
                output += (m.getKey() + " (x" + m.getValue() + ")\n");
            }else{
                output += (m.getKey() + "\n");
            }
        }

        return output;
    }
    // public void WriteFile_Total(){

    // }
}

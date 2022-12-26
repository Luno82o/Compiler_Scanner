import java.io.FileWriter;
import java.util.Map;

public class Output {
    
    public Output(){}



    public void WriteFile(Map<String, Integer> tokens){
        
        Scanner scanner = new Scanner();

        try {
            FileWriter writer = new FileWriter("output.txt");
            writer.write("Total: " + scanner.total_token() + "tokens\n");
            writer.write("");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

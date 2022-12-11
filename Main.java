import java.io.BufferedReader;
import java.io.FileReader;

public class Main {	
    public static void readTxt() {
        System.out.print("filename(xxx.txt):");
        String filename = "test.txt";

        //��J�ɦW
//        java.util.Scanner input = new java.util.Scanner(System.in);
//        String filename = input.nextLine();
//        input.close();
        
        try {
            //Ū���ɮ�
        	
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String inputLine;
            
    		Scanner sc = new Scanner();
            //�v��Ū��
            while (br.ready()) {
            	inputLine = br.readLine();
            	System.out.println(inputLine);
            	sc.scanner(inputLine);
            	
            }
            
            fr.close();
        }catch (Exception e) {
        	System.out.println("[Error] "+filename+" can't be opened.\n");
        }
    	
    }
    
    

	public static void main(String[] args) {
		readTxt();
	}
	
}

public class Main {	
    
	public static void main(String[] args) {
	    Scanner sc = new Scanner();

		Output output = new Output();
	    
        //輸入檔名
        System.out.println("filename(xxx.txt):");

        String filename = "test2.txt";
        
	    sc.readTxt(filename);
	    sc.scan();
	    sc.pAllMap();

		output.WriteFile();
	}
	
}

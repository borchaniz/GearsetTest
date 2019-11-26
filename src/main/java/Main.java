import java.io.*;

public class Main {

    public static void main(String[] args) {
        String strLine = "";
        try {
            PDFDocProcessor docProcessor = new PDFDocProcessor("./document.pdf");
            FileInputStream fstream = new FileInputStream("input");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                docProcessor.processLine(strLine);
            }
            //Close the input stream
            fstream.close();
            docProcessor.finalize();
        }catch (IOException e){
            System.out.println("IOException caught, the specified file does not exist perhaps, or you do not have permission to write to this directory");
        }

    }
}

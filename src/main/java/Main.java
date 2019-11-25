import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        String strLine = "";
        try {
            FileInputStream fstream = new FileInputStream("input");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
            }
            //Close the input stream
            fstream.close();
        }catch (IOException e){
            System.out.println("IOException caught, the specified file does not exist perhaps?");
        }

    }
}

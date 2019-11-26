import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class PDFDocProcessor {
    private Document document;
    private Paragraph currentParagraph = new Paragraph();


    PDFDocProcessor(String destination) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        document = new Document(pdfDoc);
    }

    void processLine(String line){
        if (line.equals(".paragraph") && !currentParagraph.isEmpty()){
            document.add(currentParagraph);
            currentParagraph = new Paragraph();
        }else if (line.equals(".fill")){

        }else if(line.equals(".nofill")){

        }else if (line.equals(".regular")){

        }else if (line.equals(".italic")){

        }else if(line.equals(".bold")){

        }else if (Pattern.matches("^\\.indent +[0-9]*",line)){

        }else if (Pattern.matches("^\\.indent -[0-9]*",line)){

        }else{

        }

    }

    public void finalize(){
        document.close();
    }
}

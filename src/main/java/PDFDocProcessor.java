import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class PDFDocProcessor {
    private Document document;
    PDFDocProcessor(String destination) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        document = new Document(pdfDoc);
    }

    void processLine(String line){
        String para = "First Test";
        Paragraph paragraph = new Paragraph (para);
        document.add(paragraph);
    }

    public void finalize(){
        document.close();
    }
}

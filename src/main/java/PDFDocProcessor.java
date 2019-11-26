import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.TextChunk;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class PDFDocProcessor {
    private Document document;
    private Paragraph currentParagraph = new Paragraph();
    private int size = 0;
    private boolean italic = false;
    private boolean bold = false;
    private float indent = 0;
    private boolean fill = false;

    PDFDocProcessor(String destination) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        document = new Document(pdfDoc);
    }

    public void processLine(String line) {
        if (line.equals(".paragraph") && !currentParagraph.isEmpty()) {
            startNewParagraph();
        } else if (line.equals(".fill")) {
            startNewParagraph();
            fill = true;
        } else if (line.equals(".nofill")) {
            startNewParagraph();
            fill = false;
        } else if (line.equals(".regular")) {
            italic = false;
            bold = false;
        } else if (line.equals(".italic")) {
            italic = true;
        } else if (line.equals(".bold")) {
            bold = true;
        } else if (line.equals(".large")) {
            size = 2;
        } else if (line.equals(".small")) {
            size = 0;
        } else if (line.equals(".normal")) {
            size = 1;
        } else if (Pattern.matches("^\\.indent [\\+\\-][0-9]*", line)) {
            startNewParagraph();
            indent += 20f * Float.parseFloat(line.substring(8));
            currentParagraph.setMarginLeft(indent);
        } else {
            Text text = new Text((currentParagraph.isEmpty() ? "" : " ") + line);
            if (bold) text.setBold();
            if (italic) text.setItalic();
            switch (size) {
                case 0:
                    text.setFontSize(8f);
                    break;
                case 1:
                    text.setFontSize(11f);
                    break;
                case 2:
                    text.setFontSize(16f);
                    break;
            }
            currentParagraph.add(text);
        }
    }

    public void finalize() {
        if (!currentParagraph.isEmpty())
            document.add(currentParagraph);
        document.close();
    }

    private void startNewParagraph() {
        if (!currentParagraph.isEmpty()){
            currentParagraph.setMarginLeft(indent);
            if (fill) currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(currentParagraph);
        }
        currentParagraph = new Paragraph();
    }
}

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

    private final byte TEXT_SIZE_SMALL = 0;
    private final byte TEXT_SIZE_NORMAL = 1;
    private final byte TEXT_SIZE_LARGE = 2;

    private Document document;
    private Paragraph currentParagraph = new Paragraph();
    private byte size = TEXT_SIZE_SMALL;
    private boolean italic = false;
    private boolean bold = false;
    private float indent = 0;
    private boolean fill = false;

    PDFDocProcessor(String destination) throws FileNotFoundException {
        // Creating PDF Document
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        document = new Document(pdfDoc);
    }

    public void processLine(String line) {
        if (line.equals(".paragraph")) {
            // Starting new paragraph
            startNewParagraph();
        } else if (line.equals(".fill")) {
            //start new paragraph and change the fill mode
            startNewParagraph();
            fill = true;
        } else if (line.equals(".nofill")) {
            startNewParagraph();
            fill = false;
        } else if (line.equals(".regular")) {
            // reset text style
            italic = false;
            bold = false;
        } else if (line.equals(".italic")) {
            //set text style to italic
            italic = true;
        } else if (line.equals(".bold")) {
            //set text style to bold
            bold = true;
        } else if (line.equals(".large")) {
            //set text size to large
            size = TEXT_SIZE_LARGE;
        } else if (line.equals(".small")) {
            // set text size to small
            size = TEXT_SIZE_SMALL;
        } else if (line.equals(".normal")) {
            // reset text size
            size = TEXT_SIZE_NORMAL;
        } else if (Pattern.matches("^\\.indent [\\+\\-][0-9]*", line)) {
            //start new paragraph then set the indentation relatively to old indentation
            startNewParagraph();
            indent += 20f * Float.parseFloat(line.substring(8));
            currentParagraph.setMarginLeft(indent);
        } else {
            // if the text actually contains text
            Text text = new Text((currentParagraph.isEmpty() ? "" : " ") + line);
            if (bold) text.setBold();
            if (italic) text.setItalic();
            switch (size) {
                case TEXT_SIZE_SMALL:
                    text.setFontSize(8f);
                    break;
                case TEXT_SIZE_NORMAL:
                    text.setFontSize(11f);
                    break;
                case TEXT_SIZE_LARGE:
                    text.setFontSize(16f);
                    break;
            }
            currentParagraph.add(text);
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        if (!currentParagraph.isEmpty())
            startNewParagraph();
        document.close();
    }

    private void startNewParagraph() {
        if (!currentParagraph.isEmpty()){
            currentParagraph.setMarginLeft(indent);
            if (fill) currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(currentParagraph);
            currentParagraph = new Paragraph();
        }
    }
}

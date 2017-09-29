package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
    public static void read() throws IOException {
		/* Load an existing PDF document using the static method load() of the PDDocument class.
		 This method accepts a file object as a parameter, since this is a static method you
		 can invoke it using class name as show below. */
        File file = new File("C:/Users/Alex/Desktop/hello_world_proforma.pdf"); // Stored in database
        try {
            PDDocument document = PDDocument.load(file);
            // The PDFTextStripper class provides methods to retrieve text from a PDF document therefore,
            // instantiate this class as shown below.
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // You can read/retrieve the contents of a page from the PDF document using the getText() method
            // of the PDFTextStripper class. To this method you need to pass the document object as a
            // parameter. This method retrieves the text in a given document and returns it in the form of a
            // String object.
            String text = pdfStripper.getText(document);
            // Finally, close the document using the close() method of the PDDocument class as shown below.
            document.close();

            tokSource.tokSent(text);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
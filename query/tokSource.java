package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class tokSource {
    // 'String text' from PDFReader.java
    public static void tokSent(String text) throws FileNotFoundException {
        // Initialise tokenizer model
        InputStream modelIn = new FileInputStream("trainingModels\\en-token.bin");
        try {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);

            String tokens[] = tokenizer.tokenize(text);
            // Pass the tokenized text to posSource
            posSource.posSent(tokens);
        }
        catch (IOException e) {
        }
        finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {
                }
            }
        }
    }
}
package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

// Create a drop-down menu (GUI) to select 2-3 module codes. The proforma used for answer retrieval
// is dependant on this selection. 2-3 separate proformas will be stored within the database.

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class tokQuestion {
    public static void tok(String queryText) throws FileNotFoundException {
        InputStream modelIn = new FileInputStream("trainingModels\\en-token.bin");

        try {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);

            String tokens[] = tokenizer.tokenize(queryText);
            //System.out.println(Arrays.toString(tokens));

            // Use Tokenized Words As Argument For POS Tagging
            posQuestion.pos(tokens);
        }
        catch (IOException e) {
            e.printStackTrace();
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
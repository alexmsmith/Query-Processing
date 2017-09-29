package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class posSource {
    // List of posSentences
    static List<String>posSentences = new ArrayList<String>();
    // List of tags and tokens
    static List<String>posWords = new ArrayList<String>();
    /* List of wordSentences (use this as the output to student (answer) rather than the inclusion
     of pos tags which aren't necessary */
    static List<String>wordSentences = new ArrayList<String>();
    // List of just words
    static List<String>words = new ArrayList<String>();

    public static void posSent(String[] tokens) {

        InputStream modelIn = null;

        try {
            // Initiate POS model
            modelIn = new FileInputStream("trainingModels\\en-pos-maxent.zip"); // Currently .zip file
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);

            // Array of tags for each token
            String[] tag = tagger.tag(tokens);
            // Loop through each token, and apply the associated tag, making a posWord
            for (int i=0;i<tokens.length;i++) {
                posWords.add(tag[i] + " " + tokens[i]);
                words.add(tokens[i]);
            }
            // System.out.println("posWord: "+posWords); // Print test - posWord

            //  System.out.println("words: "+words); // Print test - words

            // Loop through each element of posWord, and add it to String posString for sentence splitting
            String posString = "";
            String wordString = "";
            for (String s : posWords) {
                posString += s + " ";
            }
            for (String w : words) {
                wordString += w + " ";
            }


            //System.out.println("posString: "+posString); // Print test - posString

            //System.out.println("wordString: "+wordString); // Print test - wordString

            // Split the posString using defined pattern and store in posSentences list
            posSentences = Arrays.asList(posString.split(Pattern.quote(" . . ")));
            //System.out.println("posSentence: "+posSentences); // Print test - posSentences

            wordSentences = Arrays.asList(wordString.split(Pattern.quote(" . ")));
            //System.out.println("wordSentence: "+wordSentences); // Print test - wordSentences
        }
        catch (IOException e) {
            // Model loading failed, handle the error
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
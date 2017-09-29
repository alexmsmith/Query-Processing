package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class posQuestion {
    // List Of KeyWords
    static List<String>keyWords = new ArrayList<String>();
    // List Of KeyTags (Answer Type Concluded By WRB Tag)
    static List<String>keyTags = new ArrayList<String>();
    public static void pos(String[] tokens) {

        InputStream modelIn = null;

        try {
            // Initiate POS model
            modelIn = new FileInputStream("trainingModels\\en-pos-maxent.zip");
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);
            // We then POS tag each of the tokens in the question
            String[] tag = tagger.tag(tokens);

            for (int i=0;i<tokens.length;i++) {
                // Tag Associated With Proper Noun, Singular (Names, Places etc)
                if (tag[i].equals("NNP")) {
                    keyWords.add(tokens[i]);
                }
                if (tag[i].equals("NN")) {
                    keyWords.add(tokens[i]);
                }
                // Tag Associated With 'When', 'Who' and 'Where'.
                // Who Tag
                if (tokens[i].equals("who") || tokens[i].equals("Who")) {
                    keyTags.add("NNP");
                }
                // Where And When Tags
                if (tag[i].equals("WRB")) {
                    if (tokens[i].equals("when") || tokens[i].equals("When")) {
                        keyTags.add("CD");
                    }else if (tokens[i].equals("where") || tokens[i].equals("Where")) {
                        keyTags.add("NNP");
                    }
                }
            }
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
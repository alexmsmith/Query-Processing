package core.query;

/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* WHAT TO DO:
 * ** Rank the scores, displaying from highest to lowest **
 * ** Read from Query UI (text and assignment ID)
 * ** Pass text into tokQuestion class and pass ID into PDFReader class
 */

public class executeNLP {

    static List<String> wordList;

    // queryText passed from QueryUI to executeNLP class
    public static void execute(String queryText) throws IOException {

        // NEED TO PASS A STRING TO tokQuestion FROM QUERY UI (Query)

        tokQuestion.tok(queryText); // Execute tokQuestion.tok method
        PDFReader.read();  // Execute PDFReader.read() method



        List<String> collaboration = new ArrayList<String>(); // List collaboration of both keyWords and keyTags

        // Loop to add all keyWords to the collaboration list
        for (int i=0;i<posQuestion.keyWords.size();i++) {
            collaboration.add(posQuestion.keyWords.get(i));
        }
        // Loop to add all keyTags to the collaboration list
        for (int i=0;i<posQuestion.keyTags.size();i++) {
            collaboration.add(posQuestion.keyTags.get(i));
        }
        //System.out.println("Collaboration: "+collaboration); // Print test : collaboration

        //** HashMap that will store each posSentence with an associated score of similarity
        HashMap<String, Double> scoreHMAP = new HashMap<String, Double>();
        // HashMap that will store each word with an associated weight of occurence
        HashMap<String, Integer> weightHMAP = new HashMap<String, Integer>();

        List<String> posSentences = posSource.posSentences;
        //System.out.println("posSentences: "+posSentences); // Print test posSentences
        List<String> wordSentences = posSource.wordSentences;

        //System.out.println("WordList: "+wordList);
        //System.out.println("posSentence: "+posSentences);

        for (int i=0;i<posSentences.size();i++) {
            weightHMAP.clear();

            // This will be a test to weigh each word of a given sentence //

            // Take the first sentence
            String sent = posSentences.get(i);
            // Make some space
            System.out.println();
            // Print Test
            System.out.println("Test: "+sent);
            // Split the sentence into a wordList
            wordList = new ArrayList<String>(Arrays.asList(sent.split(" ")));
			/* Loop through and for every word in the wordList2
			   add weight to the words and store in weightHMAP  */
            for (String w: wordList) {
                Integer n = weightHMAP.get(w);
                n = (n == null) ? 1 : ++n;
                weightHMAP.put(w, n);
            }
            // Test the output of weightHMAP
            System.out.println("Output: "+weightHMAP);

            double score = 0;

			/* Now it's time to iterate over the weightHMAP, and for
			 * every key with a value > 2, divide it by 2
			 */
            Iterator it2 = weightHMAP.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair2 = (Map.Entry)it2.next();
                // If value > 2, divide by 2
                double weight = 0;
                double integ = (Integer) pair2.getValue();

                if (integ >= 2) {
                    weight = integ / 2;
                    System.out.println(pair2.getKey() + " " + weight);
                }else if (integ < 2) {
                    weight = integ;
                    System.out.println(pair2.getKey() + " " + weight);
                }

                it2.remove();

				/* Now we want to add up all the values of the pairs within
				 * the sentence, and total it for the final score
				 */
				/* Firstly lets iterate through the collaboration list
				 * which contains the keywords/tags
				 */

                for (int j=0;j<collaboration.size();) {
                    // If a keyword/tag has been found
                    if (pair2.getKey().equals(collaboration.get(j))) {
                        score = (score + weight);
                        scoreHMAP.put(wordSentences.get(i), score);
                        j++;
                    } else if (!pair2.getKey().equals(collaboration.get(j))){
                        j++;
                    }
                }
            }

            System.out.println("TotalWeight: "+score);
        }

        List<String> unsortedAnswers = new ArrayList<String>();

        // This list needs to be displayed in the Query UI output
        List<String> sortedAnswers = new ArrayList<String>();

        Map<String, Double> map = sortByValues(scoreHMAP);
        System.out.println();
        Set sortedSet = map.entrySet();
        Iterator iterator2 = sortedSet.iterator();
        while(iterator2.hasNext()) {
            Map.Entry m2 = (Map.Entry)iterator2.next();
			/* Iterate through from a possible score of 15, decrementing by 0.5
			   to catch them decimal scores */
            for (double i=15.0;i>=1.0;i-=0.5) {
                if(m2.getValue().equals(i)) {
                    unsortedAnswers.add(m2.getKey() + ": " + m2.getValue());
                }
            }
        }
        System.out.println();
        for(int j=unsortedAnswers.size()-1;j>0;--j) {
            String answer = unsortedAnswers.get(j);
            sortedAnswers.add(answer);
        }
        System.out.println();
        System.out.println("SortedAnswers: "+sortedAnswers);
    }


    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}
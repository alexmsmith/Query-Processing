
/**
 * Created by Alex Smith.
 * @author Alex Smith.
 */

import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class partest {
	/**
	   * The main method demonstrates the easiest way to load a parser.
	   * Simply call loadModel and specify the path of a serialized grammar
	   * model, which can be a file, a resource on the classpath, or even a URL.
	   * For example, this demonstrates loading a grammar from the models jar
	   * file, which you therefore need to include on the classpath for ParserDemo
	   * to work.
	   *
	   * Usage: {@code java ParserDemo [[model] textFile]}
	   * e.g.: java ParserDemo edu/stanford/nlp/models/lexparser/chineseFactored.ser.gz data/chinese-onesent-utf8.txt
	 * @throws FileNotFoundException 
	   *
	   */
	
	public static void main(String[] args) throws FileNotFoundException {
		// stanford-parser-full... should be in workspace; separate from the project files
		String parserModel = "/home/alexsmith/Desktop/stanford-parser-full-2016-10-31/stanford-parser-3.7.0-models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		if (args.length > 0) {
			parserModel = args[0];
		}
		LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);
		
		if (args.length == 0) {
			demoAPI(lp);
		} 
	}
	
	/**
	   * demoAPI demonstrates other ways of calling the parser with
	   * already tokenized text, or in some cases, raw text that needs to
	   * be tokenized as a single sentence.  Output is handled with a
	   * TreePrint object.  Note that the options used when creating the
	   * TreePrint can determine what results to print out.  Once again,
	   * one can capture the output by passing a PrintWriter to
	   * TreePrint.printTree. This code is for English.
	   */
	
	
	public static void demoAPI(LexicalizedParser lp) throws FileNotFoundException {
		
		// This option shows parsing a list of correctly tokenized words
		
		InputStream modelIn = new FileInputStream("/home/alexsmith/Desktop/apache-opennlp-1.7.2/en-token.bin");
		
		try {
			// Tokenize sentence into tokens(words)
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			
			// Create scanner object for question submission
			Scanner sc = new Scanner(System.in);
			// NOTE: Will design GUI interface later on
			System.out.println("Enter question: ");
			String question = sc.nextLine();
			String tokens[] = tokenizer.tokenize(question);
			
			List<CoreLabel> rawWords = SentenceUtils.toCoreLabelList(tokens);
			Tree parse = lp.apply(rawWords);
			parse.pennPrint();
			System.out.println();
			
			// PennTreebankLanguagePack for English
			TreebankLanguagePack tlp = lp.treebankLanguagePack();
			GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
			GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
			System.out.println(tdl);
			System.out.println();
			
			// You can also use a TreePrint object to print trees and dependencies
			TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
			tp.printTree(parse);
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

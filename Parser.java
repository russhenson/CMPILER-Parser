import java.io.*;
import java.util.*;

public class Parser {


    HashMap<String, Rule> createRules() throws IOException {
        FileReader grammarfile = new FileReader("grammar.txt");
        FileWriter outputfile = new FileWriter("outputfile.txt");
        BufferedReader reader = new BufferedReader (grammarfile);
        BufferedWriter writer = new BufferedWriter(outputfile);

        String input = reader.readLine();
        ArrayList<String> grammarLine = new ArrayList<>();


        String definition = ":=";
        String alternation = "|";
        String nonTerminal = null;


        while(input != null){
        
            grammarLine.add(input);

            input = reader.readLine();
        }
        
        HashMap<String, Rule> rules = new HashMap<String, Rule>();

        String[] create = grammarLine.get(0).split(" "); // first grammar rule
        for(int i = 1; i < create.length; i++){
            nonTerminal = create[0]; // non Terminal name
            Rule = new Rule();
        }

        

        return rules;
    }


}
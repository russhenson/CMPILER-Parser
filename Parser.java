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

            Rule name = new Rule();

            name.LHS = create[0];

            create = grammarLine.get(i).split(" ");
        }

        

        return rules;
    }


    public void parse(String[] input, HashMap<String, Rule> rulesMap) {

    }


    public void expand(Stack stack, String prod) {

    }

    public void performBacktrack(Stack stack, String prod, HashMap<String, Rule> rulesMap){
        if(stack.isEmpty()) {
            return;
        }

        stack.pop();
        String LHS = rulesMap.findKey(prod);
    }

}

/* import java.util.Stack;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.LinkedHashMap;

public class Parser {

  String output = "";
  int finalptr = 0;

  static class Rule {
    String LHS;
    ArrayList<String> RHS = new ArrayList<>();    
  }

  LinkedHashMap<String, Rule> createRules(){
    LinkedHashMap<String, Rule> allRules = new LinkedHashMap<String, Rule>();
    BufferedReader bfr = null;
    
    try {
      bfr = new BufferedReader( new FileReader("C:\\Users\\Raeanne\\Desktop\\parser-indv\\grammar.txt"));
      String line;

      while ((line = bfr.readLine()) != null) {
        Rule rule = new Parser.Rule();
        String[] token = line.split(":");
        rule.LHS = token[0].trim();
        String[] right = token[1].replaceAll(";", "").trim().split("\\|", -1);
        for (String r: right) {
          rule.RHS.add(r.trim());
        }

        allRules.put(token[0], rule);
      }

      bfr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return allRules;
  }

  public void expand(Stack<String> stack, String rhs){
    if (stack.isEmpty()){
        return;
    }

    stack.pop();
    String[] r = rhs.split(" ");
    for (int i = r.length - 1; i > -1; i--) {
      if (r[i] != "")
        stack.push(r[i]);
    }
  }

  public void setOutput(String o){
    this.output = o;
  }

  public void setFinalPtr(int p){
    this.finalptr = p;
  }

  public String getOutput(){
    return this.output;
  }

  public int getFinalPtr(){
    return this.finalptr;
  }

  public void parse(ArrayList<String> input, LinkedHashMap<String, Rule> ruleList) {
    Stack<String> stack = new Stack<>();

    String key = ruleList.keySet().stream().findFirst().get();
    stack.push(key);

    int ptr = 0;
    
    recursiveDescent(input, ptr, ruleList.get(key), stack, ruleList);
  }

  public void recursiveDescent(ArrayList<String> input, int ptr, Rule rule, Stack<String> stack, LinkedHashMap<String, Rule> ruleList) {
    if (rule != null){
      for(String rhs: rule.RHS){
        Stack<String> parserStack = new Stack<>();
        parserStack.addAll(stack);
        int currPtr = ptr;

        expand(parserStack, rhs);

        if(currPtr >= input.size()){
          if(parserStack.isEmpty()){
            setOutput("trigger accept");
            return;
          }else {
            if(Character.isUpperCase(parserStack.peek().charAt(0))){
              Rule newRule = ruleList.get(parserStack.peek());
              recursiveDescent(input, currPtr, newRule, parserStack, ruleList);
            }
          }
        } else {
          if(!parserStack.isEmpty()){
            if(parserStack.peek().equals(input.get(currPtr))){
              currPtr++;
              if(currPtr >= getFinalPtr()){
                setFinalPtr(currPtr);
              }else {
                setFinalPtr(this.finalptr);
              }
              parserStack.pop();
              recursiveDescent(input, currPtr, null, parserStack, ruleList);
            }else if(Character.isUpperCase(parserStack.peek().charAt(0))){
              Rule newRule = ruleList.get(parserStack.peek());
              recursiveDescent(input, currPtr, newRule, parserStack, ruleList);
            }
          }
        }
      }
    } else {
      Stack<String> parserStack = new Stack<>();
      parserStack.addAll(stack);
      int currPtr = ptr;

      if(!parserStack.isEmpty() && currPtr >= input.size()){
        if (Character.isUpperCase(parserStack.peek().charAt(0))) {
          Rule newRule = ruleList.get(parserStack.peek());
          recursiveDescent(input, currPtr, newRule, parserStack, ruleList);
        }
      } else {
        if(parserStack.peek().equals(input.get(currPtr))){
          currPtr++;
          if(currPtr >= getFinalPtr()){
            setFinalPtr(currPtr);
          }else {
            setFinalPtr(this.finalptr);
          }
          parserStack.pop();
          recursiveDescent(input, currPtr, null, parserStack, ruleList);
        }else if(Character.isUpperCase(parserStack.peek().charAt(0))){
          Rule newRule = ruleList.get(parserStack.peek());
          recursiveDescent(input, currPtr, newRule, parserStack, ruleList);
        }
      }
    }
  }
} */
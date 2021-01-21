import java.io.*;
import java.util.*;

public class Parser {

    static class Rule {
      public String LHS;
      public ArrayList<String> RHS = new ArrayList<>();
    }


    LinkedHashMap<String, Rule> createRules() throws IOException {
        FileReader grammarfile = new FileReader("grammar.txt");
        BufferedReader reader = new BufferedReader (grammarfile);

        String input = reader.readLine();
        ArrayList<String> grammarLine = new ArrayList<>(); 

        while(input != null){
        
            grammarLine.add(input);

            input = reader.readLine();
        }
        
        LinkedHashMap<String, Rule> rules = new LinkedHashMap<String, Rule>();



        String[] grammarTokens = null; 

        for(int j = 0; j < grammarLine.size(); j++){
          grammarTokens = grammarLine.get(j).split(" ");

          /* System.out.println("grammarLine: " + j); */

          

          Rule rule = new Parser.Rule();

          String nonTerminal =  grammarTokens[0].substring(0, grammarTokens[0].length() - 1); //remove the colon
          rule.LHS = nonTerminal;

          /* System.out.println(nonTerminal); */

          for(int i = 1; i < grammarTokens.length; i++){
                      
            rule.RHS.add(grammarTokens[i]);

            /* System.out.println(grammarTokens[i]); */
            
          }

          /* for(int i = 0; i < rule.RHS.size(); i++){
            System.out.println(rule.RHS.get(i));
          }
          System.out.println("\n"); */

          


          rules.put(nonTerminal, rule);
        }

          
      
        reader.close();
        

        return rules;
  }

  public ArrayList<String> processedInput(String inputLine){

    ArrayList<String> inputLineTokens = new ArrayList<>(); //tokens per line
    char ch;

    int i = 0, start = 0;
    while(i < inputLine.length()){

      ch = inputLine.charAt(i);

      if(ch == ' ' || ch == '\n'){
        inputLineTokens.add(inputLine.substring(start, i));
        start = i+1;
      }
      
      i++;

    }
    inputLineTokens.removeAll(Arrays.asList("",null));


    return inputLineTokens;

  }


  public void parse(ArrayList<String> inputLineTokens, LinkedHashMap<String, Rule> rulesMap) {
    Stack<String> stack = new Stack<String>();
    
    String firstRule = rulesMap.entrySet().stream().findFirst().get().getKey();

    /* System.out.println(firstRule); */



  }


  /* public void expand(Stack stack, String prod) {

  }

  public void performBacktrack(Stack stack, String prod, HashMap<String, Rule> rulesMap){
      if(stack.isEmpty()) {
          return;
      }

      stack.pop();
      String LHS = rulesMap.findKey(prod);
  } */
   

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

  LinkedHashMap<String, Rule> grammarTokensRules(){
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
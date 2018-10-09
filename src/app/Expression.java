package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	StringTokenizer st = new StringTokenizer(expr, " \t+-*/()[]");
    	String newExpr = spaces(expr);
    	
    	ArrayList<String> names = new ArrayList<String>();
    	String variable;
    	char var;
    	char next;
    	int indOfVar;
    	while (st.hasMoreTokens()) {
    		variable = st.nextToken(); // current variable in the expression
    		if(!names.contains(variable)) {
            indOfVar = newExpr.indexOf(" " + variable + " "); // gives u the index of the var in expr
            if(newExpr.length() > indOfVar+variable.length()+2) { //if the string is long enough
            next = newExpr.charAt(indOfVar+variable.length()+2); // gives u the char after the var
            }else {
            	next = '&';
            }
            var = variable.charAt(0);
            if(!Character.isDigit(var)) {
            	if(next == '[') { //its an array
            		arrays.add(new Array(variable));
            		names.add(variable);
            	}else { //its a variable
            		vars.add(new Variable(variable));
            		names.add(variable);
            	}
            }else {
            	continue;
            } 
    		}else {
    			continue;
    		}
        }
    }
    	
    //Helper method for makeVariableLists
    	private static String spaces(String expr) {
    		int i = 0;
    		String newExpr = "";
    		while(i<expr.length()) {
    			if(Character.isLetter(expr.charAt(i))) {
    				newExpr = newExpr +" ";
    				newExpr = newExpr + expr.charAt(i);
    				i++;
    				while(i<expr.length() && Character.isLetter(expr.charAt(i))) {
    					newExpr = newExpr + expr.charAt(i);
    					i++;
    				}
    				newExpr = newExpr + " ";
    			}else {
    				newExpr = newExpr + expr.charAt(i);
    				i++;
    			}
    		}
    		return newExpr;
    	}
    	
    	
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    	//remember to put in a loop deleting all the spaces
    	Stack<Float> operands = new Stack<Float>(); //stack for numbers
    	Stack<Character> operators = new Stack<Character>(); //stack for operations
    	Stack<String> arrStack = new Stack<String>(); //stack for arrays
    	String newExpr = "";
    	boolean isNegative = false;
    	
    	//gets rid of all the spaces
    	for(int i=0; i<expr.length(); i++) {
    		if(expr.charAt(i) == ' ') {
    			continue;
    		}else {
    			newExpr = newExpr + expr.charAt(i);
    		}
    	}
    	StringTokenizer expression = new StringTokenizer(newExpr,"+-*/()[]",true);
    	//base case
    	if(expression.countTokens() == 1) {
    		String token = expression.nextToken();
    		char charToken = token.charAt(0);
    		if(Character.isLetter(charToken)) {
    			//then it's a variable
				return varValue(token,vars);
    		}else{	
    			return Float.parseFloat(token);
    		}
    	}else if(expression.countTokens() == 2) { //if input is like -3
    		expression.nextToken();//get rid of -
    		String token = expression.nextToken();
    		char charToken = token.charAt(0);
    		if(Character.isLetter(charToken)) {
    			//then it's a variable
				return -1*varValue(token,vars);
    		}else{
    			return -1*(Float.parseFloat(token));
    		}
    	}
    	while(expression.hasMoreTokens()) {
    		String token = expression.nextToken(); //takes out token
    		char charToken = token.charAt(0); //makes a char version
    		//check for parenthesis
    		if(charToken == '(') {
    			operators.push(charToken);
    			
    		}else if(charToken == ')') {
    			String miniExp = "";
    			boolean popVar = true;
    			float num = 0;
    			while(operators.peek() != '(') {
    				if(popVar) {
    					boolean valuesSet = false;
    					float two = 0;
    					float one = 0;
    					if(!operands.isEmpty()) {
    						two = operands.pop();
    					}
    					if(!operands.isEmpty()) {
        					one = operands.pop();
        					valuesSet = true;
    					}
    					if(valuesSet==false) {//if there's a negative
    						num = (-1*two);
    						miniExp = miniExp + num;
    						break;
    					}
    					char op = operators.pop();
    					if(op == '+') {
    						num = one+two;
    						miniExp = miniExp + num;
    					}else if(op == '-') {
    						num = one-two;
    						miniExp = miniExp + num;
    					}else if(op == '*') {
    						num = one*two;
    						miniExp = miniExp + num;
    					}else {
    						num = one/two;
    						miniExp = miniExp + num;
    					}
    					popVar = false;
    				}else {
    					
    						char op = operators.pop();
    					
    					float two = Float.parseFloat(miniExp);
    					float one = operands.pop();
    					
    					if(op == '+') {
    						num = one+two;
    						miniExp = ""+ num;
    					}else if(op == '-') {
    						num = one-two;
    						miniExp = ""+ num;
    					}else if(op == '*') {
    						num = one*two;
    						miniExp = ""+ num;
    					}else {
    						num = one/two;
    						miniExp = ""+ num;
    					}	
    					popVar = true;
    				}
    			}
    			if(miniExp.length()==0) {//if it's the only thing in ()
    				miniExp = miniExp + operands.pop();
    				
    			}
    			operators.pop(); //gets rid of (
    			if(miniExp.indexOf("E") != -1) {
    				operands.push(num);
    			}else {
    				float ans = evaluate(miniExp,vars,arrays);
        			operands.push(ans);
    			}
    			
    			
    			
    		}else if(charToken == '[') {//check for brackets
    			operators.push(charToken);
    			
    		}else if(charToken == ']') {
    			String miniExp = "";
    			boolean popVar = true;
    			float num = 0;
    			while(operators.peek() != '[') {
    				if(popVar) {
    					boolean valuesSet = false;
    					float two = 0;
    					float one = 0;
    					if(!operands.isEmpty()) {
    						two = operands.pop();
    					}
    					if(!operands.isEmpty()) {
        					one = operands.pop();
        					valuesSet = true;
    					}
    					if(valuesSet==false) {//if there's a negative
    						num = (-1*two);
    						miniExp = miniExp + num;
    						break;
    					}
    					char op = operators.pop();
    					if(op == '+') {
    						num = one+two;
    						miniExp = miniExp + num;
    					}else if(op == '-') {
    						num = one-two;
    						miniExp = miniExp + num;
    					}else if(op == '*') {
    						num = one*two;
    						miniExp = miniExp + num;
    					}else {
    						num = one/two;
    						miniExp = miniExp + num;
    					}
    					popVar = false;
    				}else {
    					
    						char op = operators.pop();
    					
    					float two = Float.parseFloat(miniExp);
    					float one = operands.pop();
    					
    					if(op == '+') {
    						num = one+two;
    						miniExp = ""+ num;
    					}else if(op == '-') {
    						num = one-two;
    						miniExp = ""+ num;
    					}else if(op == '*') {
    						num = one*two;
    						miniExp = ""+ num;
    					}else {
    						num = one/two;
    						miniExp = ""+ num;
    					}	
    					popVar = true;
    				}
    			}
    			if(miniExp.length()==0) {//if it's the only thing in []
    				miniExp = miniExp + operands.pop();
    			}
    			operators.pop(); //get rid of [
    			if(miniExp.indexOf("E") != -1) {
    				operands.push(arrValue(arrStack.pop(),(int)num,arrays));
    			}else {
    				int index = (int)evaluate(miniExp,vars,arrays);
        			operands.push(arrValue(arrStack.pop(),index,arrays));
    			}
    			
    			
    		}else if(Character.isLetter(charToken)) {
    			//then it's a variable, check to see if it's arr or var
    			if(varOrArr(token,arrays,vars)==0) { //it's a variable
    				operands.push(varValue(token,vars));
    			}else {//it's an array
    				arrStack.push(token); //put the array on hold in stack
    			}
    		}else {
    			 if(Character.isDigit(token.charAt(token.length()-1))){
    				operands.push(Float.parseFloat(token));
    			}else {
    				while(!operators.isEmpty() && checkPrec(operators.peek(),charToken)) {
    					boolean valuesSet = false;
    					float two = 0;
    					float one = 0;
    					if(!operands.isEmpty()) {
    						two = operands.pop();
    					}
    					if(!operands.isEmpty()) {
        					one = operands.pop();
        					valuesSet = true;
    					}
    					if(valuesSet==false) {//if there's a negative
    						operands.push(-1*two);
    						operators.pop(); //get rid of the -
    						break;
    					}
    					
    					if(isNegative == true) {
    		    			char op = operators.pop();
    		    			if(op == '+') {
    		    				operands.push(one-two);
    		    			}else if(op == '-') {
    		    				operands.push(one+two);
    		    			}else if(op == '*') {
    		    				operands.push((one*two)*-1);
    		    			}else {
    		    				operands.push((one/two)*-1);
    		    			}
    		    			isNegative = false;
    		    		}
    					char op = operators.pop();
    					if(op == '+') {
    						operands.push(one+two);
    					}else if(op == '-') {
    						operands.push(one-two);
    					}else if(op == '*') {
    						operands.push(one*two);
    					}else {
    						operands.push(one/two);
    					}
    				}
    				operators.push(charToken);
    			}
    		}
    	}//all tokens are done but stack has one pair left
    	while(!operators.isEmpty()) {
    		
			boolean valuesSet = false;
			float two = 0;
			float one = 0;
			if(!operands.isEmpty()) {
				two = operands.pop();
			}
			if(!operands.isEmpty()) {
				one = operands.pop();
				valuesSet = true;
			}
			if(valuesSet==false) {
				operands.push(-1*two);
				break;
			}
				
    		if(isNegative == true) {
    			char op = operators.pop();
    			if(op == '+') {
    				operands.push(one-two);
    			}else if(op == '-') {
    				operands.push(one+two);
    			}else if(op == '*') {
    				operands.push((one*two)*-1);
    			}else {
    				operands.push((one/two)*-1);
    			}
    			isNegative = false;
    			break;
    		}
    		
			char op = operators.pop();
			if(op == '+') {
				operands.push(one+two);
			}else if(op == '-') {
				operands.push(one-two);
			}else if(op == '*') {
				operands.push(one*two);
			}else {
				operands.push(one/two);
			}
			
    	}
    	
    	if(isNegative==true) {
    		return -1*operands.pop();
    	}else {
    		return operands.pop();
    	}
    	
    	
    }
    
  //helper method to find array value
    private static float arrValue(String name, int ind, ArrayList<Array> list) {
    	for(int i=0; i<list.size(); i++) {
    		if(list.get(i).name.equals(name)) {
    			return list.get(i).values[ind];
    		}
    	}
    	return -5;
    }
    
    //helper method identifies if var is array or variable, 0 for var, -1 for arr
    private static int varOrArr(String name,ArrayList<Array> arrList, ArrayList<Variable> varList) {
    	for(int i=0; i<varList.size(); i++) {
    		if(varList.get(i).name.equals(name)) {
    			return 0;
    		}
    	}
    	for(int i=0; i<arrList.size(); i++) {
    		if(arrList.get(i).name.equals(name)) {
    			return -1;
    		}
    	}
    	return -100;
    }
    
    //helper method to find variable value
    private static float varValue(String name, ArrayList<Variable> list) {
    	for(int i=0; i<list.size();i++) {
    		if(list.get(i).name.equals(name)) {
    			return list.get(i).value;
    		}
    	}
    	System.out.println("its trying to find: " + name);
    	return -7;
    }
    
    //helper method to see if it's a variable
    private static boolean isVar(String name, ArrayList<Variable> list) {
    	for(int i=0; i<list.size();i++) {
    		if(list.get(i).name.equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //helper method to see if it's an array
    private static boolean isArr(String name, ArrayList<Array> list) {
    	for(int i=0; i<list.size(); i++) {
    		if(list.get(i).name.equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //helper method that checks precedence
    private static boolean checkPrec(char one, char two) { //returns true if they are of the same precedence or one is higher
    	if(one == '(' || two == '(' || one == '[' || two ==']') {
    		return false;
    	}else if(one == '+' && two == '+') {
    		return true;
    	}else if(one== '+' && two == '-') {
    		return true;
    	}else if(one == '+' && two == '*') {
    		return false;
    	}else if(one=='+' && two=='/') {
    		return false;
    	}else if(one=='-' && two=='-') {
    		return true;
    	}else if(one=='-' && two=='+') {
    		return true;
    	}else if(one=='-' && two == '*') {
    		return false;
    	}else if(one=='-' && two == '/') {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    
}

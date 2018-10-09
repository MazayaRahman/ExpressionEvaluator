package app;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tester {
	public static void main(String [] args) {

	/*ArrayList<Variable> vars = new ArrayList<>();
	ArrayList<Array> arrays = new ArrayList<>();*/
	/*String expr = "a-(b+A[B[a]])*d+3";
	StringTokenizer all = new StringTokenizer(expr, "+-/*()[]", true); 
	if(all.nextToken().equals("+")) {
		//do nothing
	}
	System.out.println(all.nextToken());
	/*while(all.hasMoreTokens()) {
		System.out.println(all.nextToken());
	}*/
		String test = "a"; String ad = "a";
		if(test.equals(ad)) {
			System.out.println("true");
		}
	
		
	//StringTokenizer st = new StringTokenizer(expr, " \t+-*/()[]");
	/*String newExpr = spaces(expr);
	System.out.println(newExpr);
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
	
	//print out the arraylists
	for(int i=0; i<vars.size(); i++) {
		System.out.print(vars.get(i));
	}
	System.out.println();
	for(int i=0; i<arrays.size(); i++) {
		System.out.print(arrays.get(i));
	}
	System.out.println();
	
	
	
	
	}
	
	public static String spaces(String expr) {
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
	}*/
	
	}//take out when uncommenting
	/*private static float numValue(String num) {
    	int f = 1;
    	int value = 0;
    	String newNum = "";
    	//gets the value before the decimal
    	for(int i = 0; i<num.length(); i++) {
    		if(num.charAt(i) != '.') {
        		newNum = newNum + num.charAt(i);
    		}else {
    			break;
    		}
    	}
    	System.out.println(newNum);
    	for(int i = newNum.length()-1; i>=0; i--) {
    		int number = Character.getNumericValue(newNum.charAt(i));
    		value += f*number;
    		f = f*10;
    	}
    	System.out.println(value);
    	if(num.indexOf(".") != -1){
    	int ind = num.indexOf(".");
    	int lastValue = 0;
    	String last = num.substring(ind+1,num.length());
    	System.out.println(last);
    	f=1;
    	for(int i = last.length()-1; i>=0; i--) {
    		int number = Character.getNumericValue(last.charAt(i));
    		lastValue += f*number;
    		f = f*10;
    	}
    	System.out.println(lastValue);
    	float decPlace =(float)(lastValue)/(float)(f);
    	System.out.println(decPlace);
    	return (float)(value+decPlace);
    	}else {
    		return value;
    	}
    }*/
	
}

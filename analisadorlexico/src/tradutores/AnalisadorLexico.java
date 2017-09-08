package tradutores;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AnalisadorLexico {
	
    ArrayList<Token> tokenTable;

	public static void main(String[] args) {
		
		String code = "; int​ ​x​ ​=​ ​7​ ​+​ ​25​ ​*​ ​52;\n"
				     +"if (​ ​x​ ​>=​ ​10​ ​ {\n"
				     +"   printf(\"É igual!\")}\n";
		
		
		StringBuffer auxBuffer = new StringBuffer();
		StringBuffer codeBuffer = new StringBuffer(code);
		
		TokenTable tokenTable = new TokenTable();
		tokenTable.add(new Token("int","RESERVADO",0));
		tokenTable.add(new Token("if","RESERVADO",0));
		tokenTable.add(new Token("printf","RESERVADO",0));
		tokenTable.add(new Token(";","PONTO_VIRGULA",1));
		tokenTable.add(new Token("*","ASTERISCO",1));
		tokenTable.add(new Token("(","ABRE_PAREN",1));
		tokenTable.add(new Token(")","FECHA_PAREN",1));
		tokenTable.add(new Token("{","ABRE_COL",1));
		tokenTable.add(new Token("}","FECHA_COL",1));
		
		
	    System.out.println(code+"\nINICIANDO ANALISE...\n");
	    

	    for (int x=0; x < codeBuffer.length(); x++) {
	        Token t = new Token();
        	if (auxBuffer.length()>0) {
        		 if ( (t = tokenTable.searchByToken(auxBuffer.toString())) != null  ) {
        			 if (t.getToken().equals("\"")) {
        				 x++; int y=x;
        				 
        				 while (codeBuffer.substring(x,x+1) != "\"") x++;
        				 System.out.println(codeBuffer.substring(y,x));

        			 }
        			 
        			 System.out.println(t.getToken()+"|"+t.getName());
        			 auxBuffer.setLength(0);
        		 }
        	}
	        
	        if ( (t = tokenTable.searchByToken(codeBuffer.substring(x,x+1))) != null  ) {
	        	if (t.getType() == 1) {
	        		System.out.println(t.getToken()+"|"+t.getName());
	        		}
	        } else {
	        	auxBuffer.append(codeBuffer.substring(x,x+1));
	        	}
	    }

}}

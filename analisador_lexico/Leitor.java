import java.io.*;

public class Leitor {

	private BufferedReader reader; // Leitor
	private char caracterAtual; // caracter atual

	private static final char EOF = (char) (-1);

	// caracter do fim do arquivo

	public Leitor(String file) {
		try {
             reader = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// l� primeiro caracter
		caracterAtual = read();
	}

    private char read() {
		try {
			return (char) (reader.read());
		} catch (IOException e) {
			e.printStackTrace();
			return EOF;
		}
	}
    
    // verifica se � um n�mero
    private boolean isNumero(char c) {
		if (c >= '0' && c <= '9')
			return true;

		return false;
	}
    
    // verifica se � caracter inclu�do no alfabeto
    private boolean isLetra(char c){
		if(c>='a' && c<='z' )
		return true;
		if(c>='A' && c<='Z' )
		return true;	
		
		return false;
	}
    
    
    public Token nextToken(ListaTabela tabela) {
		int state = 1; // status inicial
		int bufferNumeros = 0; // buffer de numeros
		String bufferLetras = ""; // buffer de letras
		int bufferDecimal=0;//buffer n�mero decimal
        
		boolean skipped = false;
		
		
		while (true) {
			if (caracterAtual == EOF && !skipped) {
				skipped = true;
			}
			else if (skipped) {
				try {
					reader.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		
		switch (state) {
			// Controller
			case 1:
				switch (caracterAtual) {
					case ' ': // Whitespaces
					case '\n':
					case '\b':
					case '\f':
					case '\r':
					case '\t':
					
						caracterAtual = read(); // caso for algum case acima, ignora
						continue;
					case '=':
						caracterAtual = read();
						state = 8;
						continue;
					case '!':
						caracterAtual = read();
						state = 9;
						continue;
					case '&':
						caracterAtual = read();
						state=10;
						continue;
					case '|':
						caracterAtual = read();
						state=11;
						continue;
					case '"':
						caracterAtual = read();
	                    state=13;
	                    bufferLetras="";
	                    continue;
					case '/':
						caracterAtual = read();
	                    state = 14;
	                    continue;
	                default:
	                	String s = ""+caracterAtual;
						Token t1 = tabela.search(s);
						if(t1 == null ){ //se na tabela tiver o token, verifica cada um dos cases abaixo
							state = 2; // checar nova possibilidade
							continue;
						} else {
							caracterAtual = read();
		                	return t1;
						}
				}
					
						
			// identificador de n�meros	inicial	
			case 2:
				if (isNumero(caracterAtual)) {
					bufferNumeros = 0; // Reseta o buffer
					bufferNumeros += (caracterAtual - '0');
					state = 3; //continua para saber se tem mais n�meros                 
					caracterAtual = read(); //l� proximo caracter                       
				} else {
					state=5; // se n�o for n�mero pular para case 5
				}
				continue;

			// identifica numeros
			case 3:
				if (isNumero(caracterAtual)) {
					bufferNumeros *= 10;
					bufferNumeros += (caracterAtual - '0');

					caracterAtual = read();
                                        
				}else if(caracterAtual=='.'){
					caracterAtual = read();	 
					state = 4; //n�mero decimal
				}else {
					return new Token("Number", "" + bufferNumeros);
				}
				continue;
				
			// monta e retorna numeros inteiro
			case 4:
				if (isNumero(caracterAtual)) {
					bufferDecimal = 0;
					bufferDecimal += (caracterAtual - '0');
					state = 7;
					caracterAtual = read();	
                                        
				}else {
					return new Token("ERROR", "Invalid input: "+bufferNumeros+". " );
				}
				continue;
			// numeros decimais
			case 7:
				if (isNumero(caracterAtual)) {
					bufferDecimal *= 10;
					bufferDecimal += (caracterAtual - '0');
					caracterAtual = read();
				} else {
					return new Token("Number", "" + bufferNumeros+". "+bufferDecimal);
				}
				continue;

			// identificador de palavras
			case 5:
				if(isLetra(caracterAtual)|| caracterAtual=='_'){
				bufferLetras = "";					
				bufferLetras += caracterAtual;
				state = 6;
				caracterAtual = read();
				} else {
					bufferLetras = "";
				    bufferLetras += caracterAtual;
				    caracterAtual = read();
					return new Token("ERROR", "Invalid input: "+bufferLetras);
				}
				continue;	
			
			// monta e retorna token de palavras ou variaveis
			case 6:
				if ((isLetra(caracterAtual) || isNumero(caracterAtual) || caracterAtual=='_')) {
					bufferLetras += caracterAtual;
					caracterAtual = read();
				} else {
					Token t2 = tabela.search(bufferLetras);
					if(t2 != null)
						return t2;
					else return new Token("ID", "" + bufferLetras);
				} 
				continue;
				
			// if ==
			case 8:
				if(caracterAtual=='='){
					bufferLetras = "==";
					caracterAtual = read();
					return tabela.search(bufferLetras);
				}
				else {
					bufferLetras = "=";
					return tabela.search(bufferLetras);
				}
			//if !=
			case 9: 
				if(caracterAtual=='='){
					bufferLetras = "!=";
					caracterAtual = read();
					return tabela.search(bufferLetras);
				}
				else {
					return new Token("ERROR", "Invalid input: !");
				}
			// if &&
			case 10: 
				if(caracterAtual=='&'){
					bufferLetras = "&&";
					caracterAtual = read();
					return tabela.search(bufferLetras);
				}
				else {
					return new Token("ERROR", "Invalid input: &");
				}
			// if || 
			case 11: 
				if(caracterAtual=='|'){
					bufferLetras = "||";
					caracterAtual = read();
					return tabela.search(bufferLetras);
				}
				else {
					return new Token("ERROR", "Invalid input: |");
				}
			case 13:
				if(caracterAtual=='"'){
					caracterAtual=read();
					return new Token("Sring","\""+bufferLetras+"\"");
				} else if(caracterAtual=='\n' || caracterAtual==EOF){
					caracterAtual = read();
					return new Token("ERROR","Invalid string literal");
				} else{
					bufferLetras += caracterAtual;
					caracterAtual = read();
				}
				continue;
                         
            case 14:
            	if(caracterAtual=='/'){
            		state = 15;
            		caracterAtual=read();
            	} else if(caracterAtual=='*') {
            		state = 16;
            		bufferLetras = "/";
            		caracterAtual = read();
            	} else return tabela.search(bufferLetras);
            	continue;
            
            case 15:
            	if(caracterAtual=='\n'){
            		state = 1;
            	}
            	caracterAtual = read();
            	continue;
            	
            case 16:
            	if(caracterAtual=='*')
            		state = 17;
            	caracterAtual = read();
            	continue;
            	
            case 17:
            	if(caracterAtual=='/'){
            		caracterAtual = read();
            		state = 1;
            	} else {
            		caracterAtual = read();
            		state=16;
            	}
            	continue;       
			}
		}
	}
}

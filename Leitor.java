/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAYMONLA
 */
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
    
    // verifica se o caracter incluido no alfabeto
    private boolean isLetra(char c){
	if(c>='a' && c<='z' )
            return true;
        else if(c>='A' && c<='Z' )
            return true;	
	return false;
    }
    
    // verifica se numero
    private boolean isNumero(char c) {
	if (c >= '0' && c <= '9')
            return true;
        else return false;
    }

    public Token nextToken() {
	int state = 1; // status inicial
	String bufferLetras = ""; // buffer de letras
        int bufferNumeros = 0; //buffer numeros
        int bufferDecimal = 0; //buffer decimal
        
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
                    default:
	              	state = 2; // checar nova possibilidade
		}
		continue;
					
            // identifica numeros	
            case 2:
                if (isNumero(caracterAtual)) {
                    bufferNumeros = 0; // Reseta o buffer
                    bufferNumeros += (caracterAtual - '0');
                    state = 3; //continua para saber se tem mais n�meros                 
                    caracterAtual = read(); //le proximo caracter                       
		} else {
                    state=6; // se nao for numero pular para case 6
		}
		continue;

            // monta numero inteiro
            case 3:
		if (isNumero(caracterAtual)) {
                    bufferNumeros *= 10;
                    bufferNumeros += (caracterAtual - '0');
                    caracterAtual = read();
                } else if(caracterAtual=='.'){
                    caracterAtual = read();	 
                    state = 4; //numero decimal
		} else {
                    return new Token("Number", "" + bufferNumeros);
		}
		continue;
				
            // verifica numero decimal
            case 4:
		if (isNumero(caracterAtual)) {
                    bufferDecimal = 0;
                    bufferDecimal += (caracterAtual - '0');
                    state = 5; //numero decimal
                    caracterAtual = read();
                } else {
                    return new Token("ERROR", "Invalid input: "+bufferNumeros+". " );
		}
		continue;
            
            // monta numeros decimais
            case 5:
		if (isNumero(caracterAtual)) {
                    bufferDecimal *= 10;
                    bufferDecimal += (caracterAtual - '0');
                    caracterAtual = read();
		} else {
                    return new Token("Number", "" + bufferNumeros+". "+bufferDecimal);
		}
		continue;

            // identificador de letras
            case 6:
		if(isLetra(caracterAtual)){
                    bufferLetras = "";					
                    bufferLetras += caracterAtual;
                    state = 7;
                    caracterAtual = read();
		} else {
                    bufferLetras = "";
		    bufferLetras += caracterAtual;
		    caracterAtual = read();
                    return new Token("ERROR", "Invalid input: "+bufferLetras);
		}
		continue;	
			
            // monta e retorna token de palavras
            case 7:
		if ((isLetra(caracterAtual) || isNumero(caracterAtual) || caracterAtual=='_')) {
                    bufferLetras += caracterAtual;
                    caracterAtual = read();
		} else {
                    switch (bufferLetras) {
                        case "ENTAO":
                            return new Token("comando", "ENTAO");
                            
                        case "APOS":
                            return new Token("comando", "APOS");
                            
                        case "FRENTE":
                            return new Token("basico", "FRENTE");
                            
                        case "DIREITA":
                            return new Token("basico", "DIREITA");
                            
                        case "TRAS":
                            return new Token("basico", "TRAS");
                            
                        case "ESQUERDA":
                            return new Token("basico", "ESQUERDA");
                            
                        default:
                            return new Token("ERROR", "Invalid input:" + bufferLetras);
                    }
            }
        }
        }
    }
}

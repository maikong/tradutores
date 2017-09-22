import java.util.*;
import java.io.*;

public class Arquivo {
	
	//método leitor do arquivo de palavras reservadas, retorna lista de tokens da tabela
	public List<Token> readFile(String arq){
		List<Token> lista = new ArrayList<Token>();//lista de tokens lidos do arquivo
		try{
			FileReader fr = new FileReader(arq);
			BufferedReader in = new BufferedReader(fr);
			String line = in.readLine();
			while(line != null){
				String texto = line;
				String temp[] = texto.split(" ");
				Token s = new Token(temp[0], temp[1]);//cria token da linha lida
				lista.add(s);//add token para tabela que será retornada
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
		return lista;
	}

}
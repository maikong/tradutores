import java.util.*;
import java.io.*;

public class Arquivo {
	
	public List<Tabela> readFile(String arq){
		List<Tabela> lista = new ArrayList<Tabela>();
		try{
			FileReader fr = new FileReader(arq);
			BufferedReader in = new BufferedReader(fr);
			String line = in.readLine();
			while(line != null){
				String texto = line;
				String temp[] = texto.split(" ");
				Tabela s = new Tabela(temp[0], temp[1], temp[2]);
				lista.add(s);
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
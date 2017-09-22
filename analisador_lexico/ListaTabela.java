import java.util.*;

public class ListaTabela {
	private List<Token> lista;
	
	//construdor, é passado o endereço do arquivo com as palavras reservadas
	public ListaTabela (String arq){
		Arquivo a = new Arquivo();
		this.lista = a.readFile(arq);
	}
	
	//método para pesquisa na tabela
	public Token search(String simb){
		for(int i = 0; i < lista.size(); i++){
			if(simb.equals(lista.get(i).getLexema())){
				return lista.get(i);
			}
		}
		return null;
	}

}

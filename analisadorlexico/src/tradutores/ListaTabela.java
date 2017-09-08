package tradutores;

import java.util.*;

public class ListaTabela {
	private List<Tabela> lista;
	
	public ListaTabela (String arq){
		Arquivo a = new Arquivo();
		this.lista = a.readFile(arq);
	}
	
	public Tabela search(String simb){
		for(int i = 0; i < lista.size(); i++){
			if(simb.equals(lista.get(i).getLexema())){
				return lista.get(i);
			}
		}
		return null;
	}

}

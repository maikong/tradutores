import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Teste {
	
	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser(); //Objeto para selecao de arquivo
		String inFile = ""; 
		String outFile = "";
		
		//prover tabela fixa na classe ListaTabela..
		ListaTabela tabela = new ListaTabela("ReservedWords");
		
		
		jfc.setDialogTitle("selecione o arquivo de entrada");
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			//retorna o caminho do arquivo
			inFile = jfc.getSelectedFile().getAbsolutePath();
		}
		
		jfc.setDialogTitle("Selecione o arquivo de saída");
		if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			outFile = jfc.getSelectedFile().getAbsolutePath();
		}
		

		Leitor leitor = new Leitor(inFile);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

			Token t;

			while ((t = leitor.nextToken(tabela)) != null) {
				writer.write("["+t.getToken()+", "+t.getLexema()+"]");
			}

			writer.close(); 
			
			System.out.println("Done tokenizing file: " + inFile);
			System.out.println("Output written in file: " + outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

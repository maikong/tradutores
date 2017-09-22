import java.io.*;

public class Teste {
	
	public static void main(String[] args) {
		String inFile = "C:/Users/saymo/Desktop/sample.in";
		String outFile = "C:/Users/saymo/Desktop/Sample.out";
		ListaTabela tabela = new ListaTabela("C:/Users/saymo/Desktop/ReservedWords");

		if (args.length > 1) {
			inFile = args[0];
			outFile = args[1];
		}

		Leitor leitor = new Leitor(inFile);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

			Token t;

			while ((t = leitor.nextToken(tabela)) != null) {
				System.out.println("<"+t.getToken()+", "+t.getLexema()+">");
				writer.write("<"+t.getToken()+", "+t.getLexema()+">");
				writer.newLine();
			}

			writer.close(); 
			
			System.out.println("Done tokenizing file: " + inFile);
			System.out.println("Output written in file: " + outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

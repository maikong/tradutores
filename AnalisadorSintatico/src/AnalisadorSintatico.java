/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;

import javax.swing.JFileChooser;

/**
 *
 * @author saymo
 */
public class AnalisadorSintatico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        JFileChooser jfc = new JFileChooser(); //Objeto para selecao de arquivo
	String inFile = "";
		
	
	jfc.setDialogTitle("selecione o arquivo de entrada");
	if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //retorna o caminho do arquivo
            inFile = jfc.getSelectedFile().getAbsolutePath();
	}
		
	Sintatico sintatico = new Sintatico(inFile);
        sintatico.analisa();
        
        

    }
    
}

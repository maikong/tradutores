/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;


import java.io.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author saymo
 */
public class Teste {
    
     public static void main(String[] args) {
        
        JFileChooser jfc = new JFileChooser(); //Objeto para selecao de arquivo
	String inFile = "";
		
	
	jfc.setDialogTitle("selecione o arquivo de entrada");
	if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //retorna o caminho do arquivo
            inFile = jfc.getSelectedFile().getAbsolutePath();
	}
		
	Sintatico sintatico = new Sintatico(inFile);
        
        

    }
    
}

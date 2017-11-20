/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintatico;

import java.util.ArrayList;

/**
 *
 * @author saymo
 */
public class Sintatico {
    
    private ArrayList<Token> lista; //lista de tokens
    private Posicao posicao; //variavel com posicao da sonda
    private int contLinha = 0; // contador de linha
    private int index = 0;
    private char sentido = 'F'; // vaiavel que guarda o sentido atual da sonda
    
    public Sintatico(String file){
        Leitor leitor = new Leitor(file); //inicializa leitor
        this.lista = leitor.listaTokens(); //constroi lista de tokens
        this.posicao = new Posicao(0, 0); //inicializa posicao da sonda em 0,0
    }
    
    public void analisa(){ //metodo de analise sintatica
        //Token tokenAnt = null; //token anterior
        //Token tokenPos = null; //token posterior
        while(index < lista.size()){
            /*if(index>0 && index<lista.size()-1){
                tokenAnt = lista.get(index-1);
                tokenPos = lista.get(index+1);
            }
            else if(index == 0){
                tokenPos = lista.get(index+1);
            }
            else{
                tokenAnt = lista.get(index-1);
            }*/
            switch (lista.get(index).getToken()) {
                case "ERROR" : // verifica se token esta com erro lexico
                    System.out.println("ERRO LÉXICO! COMANDO: " + lista.get(index).getLexema() + " NÃO EXISTE NA GRAMÁTICA! LINHA: " + lista.get(index).getLinha());
                    index++;
                    continue;
                    
                case "comando": // verifica se token e um comando
                    comando(index);
                    continue;
                    
                case "basico": // verifica se token e basico
                    basico(index);
                    continue;
                    
                case "l_paren": // verifica se token e (
                    l_paren(index);
                    continue;
                    
                case "r_paren": // verifica se token e )
                    System.out.println("ERRO SINTÁTICO");
                    continue;
                    
                case "num": // verifica se token e numero
                    System.out.println("ERRO SINTÁTICO! NUMERO SEM COMANDO BASICO! LINHA:" + lista.get(index).getLinha());
                    index++;
                    continue;
            }      
        }
        System.out.println("Posição final é (" + posicao.getX() + ", " + posicao.getY() + ")");
    }
    
    private void comando(int i){ // metodo para comando
        if(i<2){
            System.out.println("ERRO SINTÁTICO! COMANDO NÃO SEGUE REGRA DA GRAMÁTICA DE 'basico comando basico'! LINHA: " + lista.get(i).getLinha());
            index++;
        }
        else{
            switch(lista.get(i).getLexema()){
                case "APOS":
                    if(basico2(i+1) && basico2(i-2)){
                        index = index + 3;
                    }
                    else {
                        System.out.println("ERRO SINTÁTICO! COMANDO NÃO SEGUE REGRA DA GRAMÁTICA DE basico comando basico! LINHA: " + lista.get(i).getLinha());
                        index++;
                    }
                case "ENTAO":
                    if(basico2(i-2) && basico2(i+1)){
                        index = index + 3;
                    }
                    else {
                        System.out.println("ERRO SINTÁTICO! COMANDO NÃO SEGUE REGRA DA GRAMÁTICA DE basico comando basico! LINHA: " + lista.get(i).getLinha());
                        index++;
                    }
            }
        }
    }
    
    private boolean basico(int i){ // metodo para basico
        if(i<lista.size()-2 && lista.get(i+2).getToken() == "comando" && lista.get(i+2).getLinha() == lista.get(i).getLinha()){
            index = index + 2;
            comando(index);
            return true;
        }
        else if(basico2(i)){
            index = index + 2;
            return true;
        }
        else{
            System.out.println("ERRO SINTÁTICO! COMANDO BÁSICO NÃO É SEGUIDO POR UM NÚMERO! LINHA: " + lista.get(i).getLinha());
            index++;
            return false;
        }
    }
    
    private boolean basico2 (int i){
         int n = Integer.parseInt(lista.get(i+1).getLexema());
         if(lista.get(i+1).getToken() == "num" && lista.get(i).getLinha() == lista.get(i+1).getLinha()){
            switch(lista.get(i).getLexema()){ // verifica qual comando basico foi solicitado e atualiza a posicao
                case "FRENTE":
                    setPosicao(n, 'F');
                    System.out.println("Comando: FRENTE " + n + " executado! " + index);
                    return true;
                case "TRAS":
                    setPosicao(n, 'T');
                    System.out.println("Comando: TRAS " + n + " executado! " + index);
                    return true;
                case "DIREITA":
                    setPosicao(n, 'D');
                    System.out.println("Comando: DIREITA " + n + " executado! " + index);
                    return true;
                case "ESQUERDA":
                    setPosicao(n, 'E');
                    System.out.println("Comando: ESQUERDA " + n + " executado! " + index);
                    return true;
                default:
                    System.out.println("ERRO LÉXICO");
                    return false;
            }
         }
         else {
            return false;
         }
    }
    
    private void setPosicao(int n, char s){
        switch(sentido){
            case 'F':
                switch(s){
                    case 'F':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        break;
                    case 'D':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        break;
                    case 'T':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        break;
                    case 'E':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        break;
                }
            case 'D':
                switch(s){
                    case 'F':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        break;
                    case 'D':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        break;
                    case 'T':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        break;
                    case 'E':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        break;
                }
            case 'T':
                switch(s){
                    case 'F':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        break;
                    case 'D':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        break;
                    case 'T':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        break;
                    case 'E':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        break;
                }
            case 'E':
                switch(s){
                    case 'F':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        break;
                    case 'D':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        break;
                    case 'T':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        break;
                    case 'E':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        break;
                }
        }
    }
    
    public void l_paren(int i){
        
    }
    
}

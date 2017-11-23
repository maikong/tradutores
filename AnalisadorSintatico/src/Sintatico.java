/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author SAYMONLA
 */
public class Sintatico {
    
    private ArrayList<Token> lista; //lista de tokens
    private Posicao posicao; //variavel com posicao da sonda
    private int index = 0;
    private char sentido = 'F'; // vaiavel que guarda o sentido atual da sonda
    private int contLinha = 1; // contador de linha
    
    public Sintatico(String file){
        Leitor leitor = new Leitor(file); //inicializa leitor
        this.lista = leitor.listaTokens(); //constroi lista de tokens
        this.posicao = new Posicao(0, 0); //inicializa posicao da sonda em 0,0
    }
    
    public void analisa(){ //metodo de analise sintatica
        while(index < lista.size()){
            analise();
        }
        System.out.println("Posição final é (" + posicao.getX() + ", " + posicao.getY() + ")");
    }
    
    private void analise(){
        if(verificaLinha()){
            switch (lista.get(index).getToken()) {
                case "ERROR" : // verifica se token esta com erro lexico
                    System.out.println("ERRO LÉXICO! COMANDO: " + lista.get(index).getLexema() + " NÃO EXISTE NA GRAMÁTICA! LINHA: " + lista.get(index).getLinha());
                    index++;
                    return;

                case "comando": // verifica se token e um comando
                    comando();
                    return;

                case "basico": // verifica se token e basico
                    basico();
                    return;

                case "l_paren": // verifica se token e (
                    index++;
                    l_paren();
                    return;

                case "r_paren": // verifica se token e )
                    System.out.println("ERRO SINTÁTICO");
                    return;

                case "num": // verifica se token e numero
                    System.out.println("ERRO SINTÁTICO! NUMERO SEM COMANDO BASICO! LINHA:" + lista.get(index).getLinha());
                    index++;
            }
        }
        else index++;
    }
    
    private void comando(){ // metodo para comando
        if(index<2){
            System.out.println("ERRO SINTÁTICO! COMANDO NÃO SEGUE REGRA DA GRAMÁTICA DE 'basico comando basico'! LINHA: " + lista.get(index).getLinha());
            index++;
            contLinha++;
        }
        else{
            //comando2(index);
            try{
                if(verificaBasico(index+1) == 1 && verificaBasico(index-2) == 1){ //verifica se há comandos basicos entre os comandos e se são validos
                    switch(lista.get(index).getLexema()){
                    case "APOS": //executa comando APOS
                        basico2(index+1);
                        basico2(index-2);
                        index = index + 3;
                        contLinha++;
                        return;
                    case "ENTAO": //executa comando ENTAO
                        basico2(index-2);
                        basico2(index+1);
                        index = index + 3;
                        contLinha++;
                        return;
                    }
                }
                else if (verificaBasico(index+1) == 2 || verificaBasico(index-2) == 2){ //retorna erro se basico não é seguido de numero
                    System.out.println("ERRO SINTÁTICO! COMANDO BÁSICO NÃO É SEGUIDO POR UM NÚMERO! LINHA: " + lista.get(index).getLinha());
                    index++;
                    contLinha++;
                }
                else { //retorna erro pois o comando não esta entre basicos
                    System.out.println("ERRO SINTÁTICO! COMANDO NÃO SEGUE REGRA DA GRAMÁTICA DE basico comando basico! LINHA: " + lista.get(index).getLinha());
                    index++;
                    contLinha++;
                }
            } catch (IndexOutOfBoundsException e){ //tratamento de erro caso a verificacao do if estoure a lista
                System.out.println("ERRO SINTÁTICO! COMANDO NÃO É SEGUIDO POR UM COMANDO BÁSICO! LINHA: " + lista.get(index).getLinha());
                index ++;
            }
        }
    }
    
    /*private void comando2 (int i){
        try{
            if("comando".equals(lista.get(i+3).getToken()) && lista.get(i).getLinha() == contLinha){
                if("APOS".equals(lista.get(i+3).getLexema())){
                    
                }
                else {
                    
                }
            }
        } catch (IndexOutOfBoundsException e){
                
        }
    }*/
    
    private void basico(){ // metodo para basico
        if(index < lista.size()-2 && "comando".equals(lista.get(index+2).getToken()) && lista.get(index+2).getLinha() == lista.get(index).getLinha()){ //verifica se basico é seguido de comando e executa o comando
            index = index + 2;
            comando();
        }
        else if(verificaBasico(index) == 1){ // executa comando basico sozinho na linha
            basico2(index);
            index = index + 2;
            contLinha++;
        }
        else{ // retorna erro se o comando basico não é seguido por numero
            System.out.println("ERRO SINTÁTICO! COMANDO BÁSICO NÃO É SEGUIDO POR UM NÚMERO! LINHA: " + lista.get(index).getLinha());
            index++;
            contLinha++;
        }
    }
    
    private boolean verificaLinha(){ //metodo que verifica se comando esta na mesma linha do index
        if(lista.get(index).getLinha() == contLinha){
            return true;
        }
        else return false;
    }
    
    private int verificaBasico(int i){ //metodo que verifica se o basico é valido
        if("num".equals(lista.get(i+1).getToken()) && lista.get(i).getLinha() == lista.get(i+1).getLinha() && "basico".equals(lista.get(i).getToken()))
            return 1;
        else if(!"num".equals(lista.get(i+1).getToken()))
            return 2;
        else return 3;
    }
    
    private void basico2 (int i){ // metodo de calculo dos comandos basicos
        int n = Integer.parseInt(lista.get(i+1).getLexema());
        switch(lista.get(i).getLexema()){ // verifica qual comando basico foi solicitado e atualiza a posicao
            case "FRENTE":
                setPosicao(n, 'F');
                System.out.println("Comando: FRENTE " + n + " executado!");
                return;
            case "TRAS":
                setPosicao(n, 'T');
                System.out.println("Comando: TRAS " + n + " executado!");
                return;
            case "DIREITA":
                setPosicao(n, 'D');
                System.out.println("Comando: DIREITA " + n + " executado!");
                return;
            case "ESQUERDA":
                setPosicao(n, 'E');
                System.out.println("Comando: ESQUERDA " + n + " executado!");
                return;
            default:
                System.out.println("ERRO LÉXICO");
            }
    }
    
    private void setPosicao(int n, char s){ //metodo de mudar posicao da sonda verificando posicao atual
        switch(sentido){
            case 'F':
                switch(s){
                    case 'F':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        return;
                    case 'D':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        return;
                    case 'T':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        return;
                    case 'E':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        return;
                }
            case 'D':
                switch(s){
                    case 'F':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        return;
                    case 'D':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        return;
                    case 'T':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        return;
                    case 'E':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        return;
                }
            case 'T':
                switch(s){
                    case 'F':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                        return;
                    case 'D':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        return;
                    case 'T':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        return;
                    case 'E':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        return;
                }
            case 'E':
                switch(s){
                    case 'F':
                        posicao.setX(posicao.getX()-n);
                        sentido = 'E';
                        return;
                    case 'D':
                        posicao.setY(posicao.getY()+n);
                        sentido = 'F';
                        return;
                    case 'T':
                        posicao.setX(posicao.getX()+n);
                        sentido = 'D';
                        return;
                    case 'E':
                        posicao.setY(posicao.getY()-n);
                        sentido = 'T';
                }
        }
    }
    
    public void l_paren(){ // metodo para comandos entre parenteses
        basico();
        if(lista.get(index).getToken() == "r_paren"){// verifica se parenteses nao fecha apos comando
            index++;
        }
        else{ 
            System.out.println("ERRO SINTÁTICO! PARÊNTESES NÃO FECHA! LINHA: " + lista.get(index).getLinha());
            index++;
        }
    }
    
}
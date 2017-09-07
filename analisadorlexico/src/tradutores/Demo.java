import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.*;

class Token {  
    private String token = "";
    private String name = "";
    private int type = -1;

    Token (String token, String name, int type){
        this.name = name;
        this.token = token;
        this.type = type;
    }

    Token () {}
    public String getToken() { return token; }
    public String getName() { return name; }
    public int getType() { return type; }

}

class TokenTable extends ArrayList<Token> {

    public Token searchByToken (String token) {
         for (int x=0; x < this.size(); x++)
            if (this.get(x).getName().equals(token)) {return this.get(x);}
        return null;
}



public class Demo extends JFrame implements ActionListener {
    private String tokens[] = {"float","\\","int"};
    private String code = "if (x=1)\nprintln(\"Hello World!\")";
    
    
    private JButton btnAnalisar;
    private JButton btnSair;
    private BorderLayout layout;
    private JTextArea txtCode;
    private JTextArea txtOut;
    private JTextArea txtTokens;

    private StringBuffer auxBuffer;
    private StringBuffer codeBuffer;

    ArrayList<Token> tokenTable;
    
    
    public Demo() {
        super("Demo");
        layout = new BorderLayout(5,5);
        Container container = getContentPane();
        container.setLayout(layout);

        btnAnalisar = new JButton("Analisar");
        btnAnalisar.addActionListener(this);
        txtCode = new JTextArea(code);
        txtOut = new JTextArea();
        
        txtTokens = new JTextArea();
        for ( int x=0; x < tokens.length; x++ )
            txtTokens.append(tokens[x]+"\n");

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new GridLayout(1,3));
        pnlCenter.add(new JScrollPane(txtCode));
        pnlCenter.add(new JScrollPane(txtTokens));
        pnlCenter.add(new JScrollPane(txtOut));
                 
        container.add(btnAnalisar,BorderLayout.SOUTH);
        container.add(pnlCenter,BorderLayout.CENTER);
  
        setSize(800,600);
        setVisible(true);
       }

    public void actionPerformed (ActionEvent event) {
        if (event.getSource() == btnAnalisar) {
            analisar();

        }

    }
    
private void analisar() {
    // Cria o buffer auxiliar para ser utlizado durante o processamento;
    auxBuffer = new StringBuffer();
    //Carregado o código do formulário da tela para o buffer de processamento
    codeBuffer = new StringBuffer(txtCode.getText());
    //Carrega a lista de tokens para ser utilizado
    StringTokenizer loadTokens = new StringTokenizer(txtTokens.getText());
    //Inicializa a carrego os tokens para Tabela de Tokens
    TokenTable tokenTable = new TokenTable(); 
    while (loadTokens.hasMoreTokens()) {tokenTable.add(new Token(loadTokens.nextToken(),"token",0));}

    System.out.println(tokenTable.toString());

 
    for (int x=0; x < codeBuffer.length(); x++) {
        Token t = new Token();
        if ( (t = tokenTable.searchByToken(codeBuffer.substring(x))) != null  ) {
            //Token encontrado na tabela
            txtOut.append(t.getToken()+"\n");
        }

    }

    }
    
}

        public void main(String[] args) {
            // TODO Auto-generated method stub
            Demo app = new Demo();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        }
    
    }
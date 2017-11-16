/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAYMONLA
 */
public class Token {
	private String token;
	private String lexema;
	
	public Token(String token, String lexema) {
		super();
		this.token = token;
		this.lexema = lexema;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

}

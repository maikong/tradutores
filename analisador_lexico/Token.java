
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

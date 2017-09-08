package tradutores;


public class Tabela {
	private String lexema;
	private String token;
	private String comentario;

	public Tabela(String lexema, String token, String comentario) {
		super();
		this.lexema = lexema;
		this.token = token;
		this.comentario = comentario;
	}

	public String getLexema() {
		return lexema;
	}

	public String getToken() {
		return token;
	}

	public String getComentario() {
		return comentario;
	}
}

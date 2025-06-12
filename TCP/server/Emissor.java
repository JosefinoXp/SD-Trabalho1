package server;

import java.io.PrintStream;

public class Emissor {

	private PrintStream saida;
	private String idUsuario;

	public Emissor(PrintStream saida, String idUsuario) {
		this.saida = saida;
		this.idUsuario = idUsuario;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void envia(String mensagem) {
		this.saida.println(mensagem);
	}
}

package client;

import java.io.PrintStream;

public class EmissorMensagem {
	private PrintStream saida;

	public EmissorMensagem(PrintStream saida) {
		this.saida = saida;
	}
	public void envia(String mensagem) {
		this.saida.println(mensagem);
	}
}

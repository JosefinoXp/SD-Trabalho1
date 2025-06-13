package client;

import java.io.PrintStream;

// Envia mensagens do cliente para o servidor.
// Usa PrintStream no OutputStream do Socket (TCP).
public class EmissorMensagem {
	private PrintStream saida;

	public EmissorMensagem(PrintStream saida) {
		this.saida = saida;
	}
	public void envia(String mensagem) {
		this.saida.println(mensagem);
	}
}

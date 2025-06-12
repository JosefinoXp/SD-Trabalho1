package client;

import java.util.Scanner;

public class ReceptorMensagem implements Runnable {
	private Scanner entrada;

	private Tela tela;

	public ReceptorMensagem(Scanner entrada, Tela tela) {
		this.entrada = entrada;
		this.tela = tela;
	}

	public void run() {
		while (this.entrada.hasNextLine()) {
			String mensagem = this.entrada.nextLine();
			this.tela.adicionaMensagem(mensagem);
		}
	}
}
package server;

import java.util.Scanner;

public class Receptor implements Runnable {
	private Scanner entrada;

	private Distribuidor distribuidor;

	private String clienteId;

	public Receptor(Scanner entrada, Distribuidor distribuidor, String clienteId) {
		this.entrada = entrada;
		this.distribuidor = distribuidor;
		this.clienteId = clienteId;
	}

	public void run() {
		while (this.entrada.hasNextLine()) {
			String mensagem = this.clienteId + ": " + this.entrada.nextLine();
			this.distribuidor.distribuiMensagem(mensagem);
		}
	}
}
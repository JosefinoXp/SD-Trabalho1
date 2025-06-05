package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket socket =
				new Socket("localhost", 8081);

		PrintStream saida = new PrintStream(socket.getOutputStream());

		Scanner entrada = new Scanner(socket.getInputStream());

		EmissorMensagem emissor = new EmissorMensagem(saida);

		Tela telaK19Chat = new Tela(emissor);

		ReceptorMensagem receptor = new ReceptorMensagem(entrada, telaK19Chat);

		Thread pilha = new Thread(receptor);
		pilha.start();

		// socket.close();
	}
}	
package server;

import java.io.IOException;
import java.net.ServerSocket;

// Ponto de entrada do servidor; inicia o ServerSocket na porta 8081.
// Cria um ServerSocket (TCP) para aceitar conexões de clientes.
public class Server {
    public static void main(String[] args) throws IOException {
        TelaServidor telaServidor = new TelaServidor();
        Distribuidor distribuidor = new Distribuidor();
        distribuidor.setTelaServidor(telaServidor);
        
        ServerSocket serverSocket = new ServerSocket(8081);
        telaServidor.adicionarLog("Servidor iniciado na porta 8081");
        
        Registrador registrador = new Registrador(distribuidor, serverSocket);
        Thread pilha = new Thread(registrador);
        pilha.start();
        
        telaServidor.adicionarLog("Aguardando conexões...");
    }
}

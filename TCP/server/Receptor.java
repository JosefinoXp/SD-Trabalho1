package server;

import java.util.Scanner;

// Thread que processa mensagens recebidas de um cliente.
// Lê mensagens do InputStream do Socket (TCP).
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
            String mensagemCompleta = this.entrada.nextLine();
            
            // Processa comandos especiais
            if (mensagemCompleta.startsWith("/")) {
                processarComando(mensagemCompleta);
            } else if (mensagemCompleta.startsWith("@")) {
                // Mensagem privada no formato: @usuario mensagem
                processarMensagemPrivada(mensagemCompleta);
            } else {
                // Mensagem pública normal
                String mensagem = this.clienteId + ": " + mensagemCompleta;
                this.distribuidor.distribuiMensagem(mensagem);
            }
        }
    }

    private void processarComando(String comando) {
        if (comando.equals("/users") || comando.equals("/lista")) {
            this.distribuidor.enviarListaUsuarios(this.clienteId);
        } else if (comando.equals("/help") || comando.equals("/ajuda")) {
            // Envia ajuda apenas para o solicitante
            for (Emissor emissor : ((Distribuidor) this.distribuidor).emissores) {
                if (emissor.getIdUsuario().equals(this.clienteId)) {
                    emissor.envia("[SISTEMA] Comandos disponíveis:");
                    emissor.envia("  @usuario mensagem - Enviar mensagem privada");
                    emissor.envia("  /users ou /lista - Listar usuários conectados");
                    emissor.envia("  /help ou /ajuda - Mostrar esta ajuda");
                    break;
                }
            }
        }
    }

    private void processarMensagemPrivada(String mensagem) {
        // Formato: @usuario mensagem
        if (mensagem.length() > 1) {
            int espacoIndex = mensagem.indexOf(' ');
            if (espacoIndex > 1) {
                String destinatario = mensagem.substring(1, espacoIndex); // Remove o @
                String conteudo = mensagem.substring(espacoIndex + 1);
                this.distribuidor.distribuiMensagemPrivada(conteudo, this.clienteId, destinatario);
            } else {
                // Formato inválido
                this.distribuidor.distribuiMensagemPrivada(
                    "[SISTEMA]: Formato correto: @usuario mensagem", 
                    "SISTEMA", 
                    this.clienteId
                );
            }
        }
    }
}

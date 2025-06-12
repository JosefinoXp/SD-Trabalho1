package server;

import java.util.ArrayList;
import java.util.Collection;

public class Distribuidor {
    public Collection<Emissor> emissores = new ArrayList<>();
    private TelaServidor telaServidor;

    public void setTelaServidor(TelaServidor tela) {
        this.telaServidor = tela;
    }

    public void adicionaEmissor(Emissor emissor) {
        this.emissores.add(emissor);
        if (this.telaServidor != null) {
            this.telaServidor.atualizarListaUsuarios(obterListaUsuarios());
            this.telaServidor.adicionarLog("Usuário conectado: " + emissor.getIdUsuario());
        }
    }

    public void removeEmissor(Emissor emissor) {
        this.emissores.remove(emissor);
        if (this.telaServidor != null) {
            this.telaServidor.atualizarListaUsuarios(obterListaUsuarios());
            this.telaServidor.adicionarLog("Usuário desconectado: " + emissor.getIdUsuario());
        }
    }

    private String[] obterListaUsuarios() {
        return this.emissores.stream()
                .map(Emissor::getIdUsuario)
                .toArray(String[]::new);
    }

    // Método para mensagens públicas (como estava antes)
    public void distribuiMensagem(String mensagem) {
        for (Emissor emissor : this.emissores) {
            emissor.envia(mensagem);
        }
        if (this.telaServidor != null) {
            this.telaServidor.adicionarLog("Mensagem pública: " + mensagem);
        }
    }

    // NOVO: Método para mensagens privadas
    public void distribuiMensagemPrivada(String mensagem, String remetente, String destinatario) {
        boolean destinatarioEncontrado = false;
        
        for (Emissor emissor : this.emissores) {
            // Envia para o destinatário
            if (emissor.getIdUsuario().equals(destinatario)) {
                emissor.envia("[PRIVADA de " + remetente + "]: " + mensagem);
                destinatarioEncontrado = true;
            }
            // Também envia para o remetente (confirmação)
            if (emissor.getIdUsuario().equals(remetente)) {
                emissor.envia("[PRIVADA para " + destinatario + "]: " + mensagem);
            }
        }
        
        if (!destinatarioEncontrado) {
            // Notifica o remetente que o usuário não foi encontrado
            for (Emissor emissor : this.emissores) {
                if (emissor.getIdUsuario().equals(remetente)) {
                    emissor.envia("[SISTEMA]: Usuário '" + destinatario + "' não encontrado.");
                }
            }
        }
        
        if (this.telaServidor != null) {
            this.telaServidor.adicionarLog("Mensagem privada de " + remetente + " para " + destinatario + ": " + mensagem);
        }
    }

    // NOVO: Método para listar usuários conectados
    public void enviarListaUsuarios(String solicitante) {
        StringBuilder lista = new StringBuilder("[SISTEMA] Usuários conectados: ");
        for (Emissor emissor : this.emissores) {
            lista.append(emissor.getIdUsuario()).append(", ");
        }
        
        for (Emissor emissor : this.emissores) {
            if (emissor.getIdUsuario().equals(solicitante)) {
                emissor.envia(lista.toString());
                break;
            }
        }
    }
}

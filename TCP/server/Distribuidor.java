package server;

import java.util.ArrayList;
import java.util.Collection;

public class Distribuidor {
    private Collection<Emissor> emissores = new ArrayList<Emissor>();
    private TelaServidor telaServidor; // Adicionar referência à tela
    
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

	public void distribuiMensagem(String mensagem) {
		for (Emissor emissor : this.emissores) {
			emissor.envia(mensagem);
		}
	}
}
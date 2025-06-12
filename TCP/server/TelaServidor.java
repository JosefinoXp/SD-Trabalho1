package server;

import javax.swing.*;
import java.awt.*;

public class TelaServidor {
    private final JFrame frame;
    private final JTextArea logArea;
    private final DefaultListModel<String> modeloUsuarios;
    private final JList<String> listaUsuarios;
    
    public TelaServidor() {
        this.frame = new JFrame("Servidor de Chat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        
        // Área de log
        this.logArea = new JTextArea(15, 40);
        this.logArea.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(this.logArea);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log do Servidor"));
        
        // Lista de usuários
        this.modeloUsuarios = new DefaultListModel<>();
        this.listaUsuarios = new JList<>(this.modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(this.listaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(200, 300));
        scrollUsuarios.setBorder(BorderFactory.createTitledBorder("Usuários Conectados"));
        
        // Layout
        this.frame.add(scrollLog, BorderLayout.CENTER);
        this.frame.add(scrollUsuarios, BorderLayout.EAST);
        
        this.frame.pack();
        this.frame.setVisible(true);
        
        adicionarLog("Servidor inicializado. Clique em 'Iniciar Servidor' para começar.");   
    }
    
    public void atualizarListaUsuarios(String[] usuarios) {
        SwingUtilities.invokeLater(() -> {
            this.modeloUsuarios.clear();
            for (String usuario : usuarios) {
                if (usuario != null && !usuario.isEmpty()) {
                    this.modeloUsuarios.addElement(usuario);
                }
            }
        });
    }
    
    public void adicionarLog(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            this.logArea.append("[" + java.time.LocalTime.now() + "] " + mensagem + "\n");
            this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
        });
    }
}

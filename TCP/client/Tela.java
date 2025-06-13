package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;

// 	Interface gráfica do cliente (chat e comandos).
public class Tela {
    private final JFrame frame;
    private final JPanel panel;
    private final JScrollPane scrollPane;
    private final JTextArea textArea1;
    private final JLabel label1;
    private final JTextField textField;
    private final JButton button;
    private final JButton buttonUsers;
    private final EmissorMensagem emissorMensagem;

    public Tela(EmissorMensagem emissor) {
        this.emissorMensagem = emissor;
        this.frame = new JFrame("Chat Multithread - Público e Privado");
        this.panel = new JPanel();
        this.textArea1 = new JTextArea(15, 60);
        this.textArea1.setEditable(false);
        this.scrollPane = new JScrollPane(this.textArea1);
        
        this.label1 = new JLabel("<html>Digite uma mensagem...<br>" +
                                "Para mensagem privada: @usuario mensagem<br>" +
                                "Comandos: /users, /help</html>");
        this.textField = new JTextField(50);
        this.button = new JButton("Enviar");
        this.buttonUsers = new JButton("Listar Usuários");

        this.frame.setContentPane(this.panel);
        this.panel.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.add(this.label1);
        
        JPanel centerPanel = new JPanel();
        centerPanel.add(this.scrollPane);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(this.textField);
        bottomPanel.add(this.button);
        bottomPanel.add(this.buttonUsers);
        
        this.panel.add(topPanel, BorderLayout.NORTH);
        this.panel.add(centerPanel, BorderLayout.CENTER);
        this.panel.add(bottomPanel, BorderLayout.SOUTH);

        class EnviaMensagemListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String texto = textField.getText().trim();
                if (!texto.isEmpty()) {
                    emissorMensagem.envia(texto);
                    textField.setText("");
                }
            }
        }

        class ListarUsuariosListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                emissorMensagem.envia("/users");
            }
        }

        this.button.addActionListener(new EnviaMensagemListener());
        this.buttonUsers.addActionListener(new ListarUsuariosListener());
        
        // Permite enviar com Enter
        this.textField.addActionListener(new EnviaMensagemListener());

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 400);
        this.frame.setVisible(true);
        
        // Adiciona mensagem de boas-vindas
        adicionaMensagem("[SISTEMA] Bem-vindo ao chat!");
        adicionaMensagem("[SISTEMA] Para enviar mensagem privada: @usuario mensagem");
        adicionaMensagem("[SISTEMA] Para listar usuários: /users");
    }

    public void adicionaMensagem(String mensagem) {
        // Destaca mensagens privadas
        if (mensagem.contains("[PRIVADA")) {
            this.textArea1.append(">>> " + mensagem + " <<<\n");
        } else if (mensagem.contains("[SISTEMA]")) {
            this.textArea1.append("*** " + mensagem + " ***\n");
        } else {
            this.textArea1.append(mensagem + "\n");
        }
        
        // Auto-scroll para a última mensagem
        this.textArea1.setCaretPosition(this.textArea1.getDocument().getLength());
    }
}

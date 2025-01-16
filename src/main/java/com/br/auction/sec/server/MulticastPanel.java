package com.br.auction.sec.server;

import com.br.auction.sec.service.MulticastService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MulticastPanel extends JPanel {
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;

    private MulticastService multicastService;

    public MulticastPanel() {
        setLayout(new BorderLayout());

        // �rea de mensagens recebidas
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Campo de entrada e bot�o de envio
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Enviar");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Inicializando servi�o multicast
        multicastService = new MulticastService("230.0.0.0", 5000, this);
        new Thread(multicastService).start();

        // Evento de clique no bot�o "Enviar"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    multicastService.sendMessage(message);
                    inputField.setText("");
                }
            }
        });
    }

    // M�todo para exibir mensagens recebidas
    public void displayMessage(String message) {
        messageArea.append(message + "\n");
    }
}

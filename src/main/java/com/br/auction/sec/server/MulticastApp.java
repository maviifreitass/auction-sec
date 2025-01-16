package com.br.auction.sec.server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MulticastApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Multicast Chat");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            MulticastPanel panel = new MulticastPanel();
            frame.add(panel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}

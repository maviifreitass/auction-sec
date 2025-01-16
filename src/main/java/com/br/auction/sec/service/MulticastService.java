package com.br.auction.sec.service;
import com.br.auction.sec.server.MulticastPanel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastService implements Runnable {
    private String multicastGroup;
    private int port;
    private MulticastSocket socket;
    private InetAddress group;
    private MulticastPanel panel;

    public MulticastService(String multicastGroup, int port, MulticastPanel panel) {
        this.multicastGroup = multicastGroup;
        this.port = port;
        this.panel = panel;

        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName(multicastGroup);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                panel.displayMessage("Recebido: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
            panel.displayMessage("Enviado: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

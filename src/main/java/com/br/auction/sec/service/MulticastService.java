package com.br.auction.sec.service;

import com.br.auction.sec.client.ClientAuctionPanel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class MulticastService implements Runnable {

    private String multicastGroup;
    private int port;
    private MulticastSocket socket;
    private InetAddress group;
    private ClientAuctionPanel panel;

    public MulticastService() {
    }

    public MulticastService(String multicastGroup, int port, ClientAuctionPanel panel) {
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
                
                JsonObject jsonObject = JsonParser.parseString(receivedMessage).getAsJsonObject();
                panel.displayMessage(jsonObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MulticastService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(JsonObject json) {
        try {            
            byte[] buffer = json.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.entity.dao;

import java.net.MulticastSocket;

/**
 *
 * @author maria
 */
public class Multicast {

    private int port;
    private MulticastSocket socket;
    private String group;

    public Multicast(int port, String group) {
        this.port = port;
        this.group = group;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return new com.google.gson.Gson().toJson(this);
    }

}

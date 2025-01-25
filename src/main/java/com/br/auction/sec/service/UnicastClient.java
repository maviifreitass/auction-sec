package com.br.auction.sec.service;

import java.io.*;
import java.net.*;

public class UnicastClient {

    public static String sendRequest(String cpf) {
        String serverAddress = "127.0.0.1";
        int port = 12345;
        String simetricKey = null;

        try ( Socket socket = new Socket(serverAddress, port);  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado ao servidor em " + serverAddress + ":" + port);

            out.println(cpf);

            simetricKey = in.readLine();

            System.out.println(simetricKey);

        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }

        return simetricKey;
    }
}

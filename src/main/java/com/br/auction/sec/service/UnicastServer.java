package com.br.auction.sec.service;

import com.br.auction.sec.db.UserDB;
import com.br.auction.sec.server.ServerStatic;
import com.br.auction.sec.util.KeyUtils;
import java.io.*;
import java.net.*;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.json.JSONObject; 

public class UnicastServer {

    public static void readRequest() throws Exception {
        int port = 12345;

        try ( ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor aguardando conexões na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                try ( BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String cpf = in.readLine(); 
                    System.out.println("CPF recebido: " + cpf);

                    UserDB userDB = new UserDB();

                    String publicKeyText = userDB.findByCpfSystem(cpf);
                    SecretKey secretKeyOriginal = ServerStatic.getSecretKey();
                    System.out.println("SECRET KEY: " + secretKeyOriginal.hashCode());
                    JSONObject sendMsg = new JSONObject(); 

                    PublicKey publicKey = KeyUtils.decodePublicKey(publicKeyText);

                    sendMsg.put("Port", encryptMessage("5000".getBytes(), publicKey));

                    sendMsg.put("Group", encryptMessage("230.0.0.0".getBytes(), publicKey));

                    sendMsg.put("IV", encryptMessage(ServerStatic.getIniVetor(), publicKey));

                    sendMsg.put("Key", encryptMessage(secretKeyOriginal.getEncoded(), publicKey));

                    out.println(sendMsg.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    public static String encryptMessage(byte[] message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = null;
        encryptedMessage = cipher.doFinal(message);

        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

}

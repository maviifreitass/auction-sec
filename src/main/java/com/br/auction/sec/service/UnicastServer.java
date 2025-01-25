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

public class UnicastServer {

    public static void readRequest() throws Exception {
        int port = 12345;

        try ( ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor aguardando conexões na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                try ( BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String cpf = in.readLine(); // Recebe o JSON
                    System.out.println("CPF recebido: " + cpf);

                    UserDB userDB = new UserDB();

                    String publicKeyText = userDB.findByCpfSystem(cpf);
                    SecretKey secretKeyOriginal = ServerStatic.getSecretKey();
                    System.out.println("CHAVE SIMETRICA>>>>  " + secretKeyOriginal);

                    PublicKey publicKey = KeyUtils.decodePublicKey(publicKeyText);
                    String encryptedKey = encryptSymmetricKey(secretKeyOriginal, publicKey);

                    /*
                        byte[] port = CriptografiaAssimetrica.do_RSAEncryption("50002", publicKey);
                        String encodedPort = java.util.Base64.getEncoder().encodeToString(port);
                        sendMsg.put("Port", encodedPort);

                        byte[] group = CriptografiaAssimetrica.do_RSAEncryption("230.0.0.0", publicKey);
                        String encodedGroup = java.util.Base64.getEncoder().encodeToString(group);
                        sendMsg.put("Group", encodedGroup);

                        //byte[] _IV = CriptografiaAssimetrica.do_RSAEncryption(IV.toString(), publicKey);
                        String encodedIV = java.util.Base64.getEncoder().encodeToString(IV);
                        sendMsg.put("IV", encodedIV);

                        byte [] byteChave = chaveSimetrica.getEncoded();
                        String stringChave = java.util.Base64.getEncoder().encodeToString(byteChave);
                        byte[] Symmetrickey = CriptografiaAssimetrica.do_RSAEncryption(stringChave, publicKey);
                        String encodedKey = java.util.Base64.getEncoder().encodeToString(Symmetrickey);
                        sendMsg.put("Chave", encodedKey);
                     */
                    out.println(encryptedKey);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    public static String encryptSymmetricKey(SecretKey secretKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(secretKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }
}
